# DuplicateHeaderMediator
A class mediator that transform duplicate transport headers to synapse properties in WSO2 EI

Instructions
* Copy DuplicateHeadersMediator-1.0-SNAPSHOT.jar inside target folder to <EI_HOME>/libs
* Start the server Add the following proxy service.
```
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
       name="TestDuplicateHeadersMediator"
       startOnLoad="true"
       statistics="disable"
       trace="disable"
       transports="http,https">
   <target>
      <inSequence>
         <class name="com.wso2.sample.DuplicateHeadersMediator"/>
         <log>
            <property expression="$trp:test" name="test1"/>
            <property expression="$ctx:test2" name="test2"/>
            <property expression="$ctx:test3" name="test3"/>
         </log>
         <respond/>
      </inSequence>
   </target>
   <description/>
</proxy>
```                              
* Invoke the proxy service using the following curl command
```
curl -X POST   http://localhost:8280/services/TestDuplicateHeadersMediator -H 'test: Hello' -H 'test: World' -H 'test: Thank You' -H 'content-type: application/json' -d '{"Hello":"world"}'
``` 
* You will receive the following logs in the terminal 
```
[2019-09-14 13:01:06,613] [EI-Core]  INFO - LogMediator To: /services/TestDuplicateHeadersMediator, MessageID: urn:uuid:a46d6c3b-dacc-45b2-a01f-16fea420a84e, Direction: request, test1 = Hello, test2 = World, test3 = Thank You
```

Happy Coding :smile: