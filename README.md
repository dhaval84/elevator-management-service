<h1>Elevator Management Service</h1>

 - For this application to work, we would require JAVA 1.8 or above
 - To Build we would require to have Maven 3.2.x installed and an internet connection


In order to build please execute<br>
'mvn clean install'

In order to run the application after a successful build, execute following command from target directory
 - cd service/target
 - java -jar service-1.0-SNAPSHOT.jar

 This will start an internal tomcat on port 8080

<h2>Assumptions</h2>

 - As hardware library is not available as library(jar), have just added it as maven module
 - Initial position of elevator is assumed to be at 0
 - 6 floors are considered as 0 to 5 floors
 - LockBreaks would trigger door open if on a floor, and unlockBreaks would trigger door close, and the solution do not need to take any care of door operations
 - Stop button locks breaks, and pressing it again unlocks breaks
 - If new elevator is to be added we would have to change the configuration and restart Service
 - Logging is just System.out.println

<h2>Additional Considerations</h2>

 - This product is just MVP
 - Currently not storing state of lifts in datastore, it's in memory, so service would retain states during restart
 - As states are in memory, it cannot scale (for scaling would require states to be stored in central datastore)
 - No Security is included as of now
 - Only one routing strategy available
 - Swagger spec is included in service/src/main/resources folder
 - Build runs unit tests and integration tests
