# intellect

This is an Assessment Application for Intellect based on Java / Maven / Spring Boot (version 1.5.1) that hosts services for User addition, updation and deletion.

HOW TO RUN
-----------
This application is packaged as a jar which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the java -jar command.

1. Clone this repository
2. Make sure you are using JDK 1.8 and Maven 3.x
3. You can build the project and run the tests by running mvn clean package
4. Once successfully built, you can run the service by : java -jar target/intellect-0.0.1-SNAPSHOT.war
5. Check the stdout or boot_example.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

     2017-11-24 22:28:54.560  INFO 5684 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
    2017-11-24 22:28:54.583  INFO 5684 --- [           main] com.StartApplication                     : Started StartApplication in 29.399 seconds (JVM running for 31.629)


ABOUT THE SERVICE
------------------
The service is just a simple user management REST service. It uses an in-memory database (H2) to store the data which writes into a file locally to persist the data.
Available services are: add, update, delete. Use the required one by adding with standard server URl like:
          
          http://{server}:{port}/user/add, http://{server}:{port}/user/update, http://{server}:{port}/user/delete
	  	      
To View all scenerio's Test cases and know about the application related status code, visit TestCasePack text file
          
INSERT A USER
-------------
    {
	    "fName":"John",
	    "lName":"Smith",
	    "email":"John.Smith@gmail.com",
	    "pinCode":123456,
	    "birthDate":"1992-11-17"
    }
    
UPDATE A USER (Only PinCOde and Date Of Birth)
----------------------------------------------
    {
      "id":"1",
	    "pinCode":123456,
	    "birthDate":"1992-11-17"
    }
    
DELETE A USER
-------------
    http://{server}:{port}/user/delete?id=1

To view your H2 in-memory datbase    
-----------------------------------
To view and query the database you can browse to http://{server}:{port}/userdatabase. Username is 'admin' with password as 'admin'. File locally saved is named intellectDB. 
    
