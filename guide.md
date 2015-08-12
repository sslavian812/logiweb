# deploy guide

 - git clone git@github.com:sslavian812/logiweb.git
 - run <b>init.sql</b> to create db tables

 - <b>mvn clean install</b>
 - <b>mvn wildfly:deploy</b>
 
 Edit META_INF/persistence.xml to change db connection settings.
 
 Default urls:
    http://localhost:8080/   - manager's page
    http://localhost:8080/ws/services/   - web services
    http://localhost:8080/ws/services/activity?wsdl   - wsdl file
    http://localhost:8080/obu/   - On Board Unit - driver's page
