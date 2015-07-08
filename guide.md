# deploy guide

 - git clone git@github.com:sslavian812/logiweb.git
 - run <b>init.sql</b> to create db and some fake data

 - add to tomcat-users.xml:
 ```xml
    <role rolename="manager-gui"/>
 	<role rolename="manager-script"/>
 	<user username="admin" password="admin" roles="manager-gui,manager-script" />
 ```

 - add to .m2/settings.xml:
 ```xml
 <servers>
   		<server>
 			<id>TomcatServer</id>
 			<username>admin</username>
 			<password>admin</password>
 		</server>
 </servers>
```

 - <b>mvn clean install</b>
 - <b>mvn tomcat7:deploy</b>
 
 Edit ./persistence.xml to change db connection settings.
 
 Default case: http://localhost:8080/
 
 P.S.: to run tests, no fake date required, but the sometimes database connection needed.