package com.gdsoft.sample;

import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import java.util.ArrayList;
import java.util.Map;

/**
 * A class mediator that transform excess transport headers into synapse properties.
 */
public class DuplicateHeadersMediator extends AbstractMediator {


    public boolean mediate(MessageContext messageContext) {
        org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext)
                .getAxis2MessageContext();
        Map excessHeaders = (Map) axis2MessageContext.getProperty("EXCESS_TRANSPORT_HEADERS");
        Map transportHeaders = (Map) axis2MessageContext.getProperty("TRANSPORT_HEADERS");
        if (excessHeaders.size() != 0 && transportHeaders.size() != 0) {
            for (Object key : transportHeaders.keySet()) {
                addPropertiesForExcessHeaders((String)key,excessHeaders,messageContext);
            }
        }
        return true;
    }

    // Add extra properties to the synapse message context for duplicated headers.
    private void addPropertiesForExcessHeaders(String headerName, Map excessHeaders, MessageContext messageContext) {
        if (excessHeaders.get(headerName) != null) {
            ArrayList<String> list = (ArrayList) excessHeaders.get(headerName);
            if (list.size() > 0) {
                int i = 2;
                for (String value : list) {
                    String propName = headerName + i;
                    messageContext.setProperty(propName, value);
                    i += 1;
                }
            }
        }
    }
}
