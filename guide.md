# deploy guide

 - git clone git@github.com:sslavian812/logiweb.git
 - create db, user and some fake data
 - mvn clean install
 - mvn tomcat:run 
 
 Edit ./persistence.xml to change db connection settings.
 
 Default case: http://localhost:8080/
 
 P.S.: to run tests, no fake date required.