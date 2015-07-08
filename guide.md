# deploy guide

 - git clone git@github.com:sslavian812/logiweb.git
 - run <b>init.sql</b> to create db and some fake data
 - <b>mvn clean install</b>
 - <b>mvn tomcat:run</b>
 
 Edit ./persistence.xml to change db connection settings.
 
 Default case: http://localhost:8080/
 
 P.S.: to run tests, no fake date required, but the sometimes database connection needed.