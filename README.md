# @Autowire MicroProfile into Spring with Quarkus
This project demonstrates using both Spring and MicroProfile APIs in the same application with Quarkus. It also covers Quarkus Live Coding and native compilation. This includes Spring DI, Spring Web, and Spring Data JPA APIs with MicroProfile Fault Tolerance, MicroProfile Health, MicroProfile Metrics, and the MicroProfile Rest Client.

Sample set of applications that use both Spring APIs and MicroProfile APIs together on Quarkus.

Applications
* **quarkus-springmp-salutation** Simple microservice that exposes a hard-coded salutation ("Hello") at a RESTful endpoint using Spring Web APIs.
* **quarkus-springmp-person** Simple microservice that uses Spring DI, Spring Web, Spring Data JPA, MicroProfile Fault Tolerance, MicroProfile Metrics, MicroProfile Health, MicroProfile Rest Client, and Hibernate ORM to manage (CRUD) a Person entity and invoke the quarkus-springmp-salutation service.

## Requirements
* Quarkus 0.22.0+
* Java 8
* Maven 3.5.3+
* GraalVM 19.2+ with native-image installed
* Docker

## Run Application in dev mode
### Start salutation application
Open a terminal window. From within the quarkus-springmp-salutation directory, type `mvn compile quarkus:dev`. This will start the salutation service listening on port 8081

### Start the person application
Open a second terminal window. From within the quarkus-springmp-person directory, type `mvn compile quarkus:dev`. This will start the salutation service listening on port 8080. When running in dev mode, the application will use an embedded H2 database. Ignore the error **port in use** error because the debug port is already being used by the salutation application.

### Test the application
1. Open http://localhost:8080/person.  The output should look like:

`[
  {
    "age": 24,
    "id": 1,
    "name": "Duke"
  },
  {
    "age": 29,
    "id": 2,
    "name": "John"
  },
  {
    "age": 33,
    "id": 3,
    "name": "Bob"
  },
  {
    "age": 25,
    "id": 4,
    "name": "Mary"
  },
  {
    "age": 30,
    "id": 5,
    "name": "Jill"
  }
]`

2. Open http://localhost:8080/person/greet/1 and keep refreshing the browser tab. Roughly 50% of the time `Howdy Duke` will be displayed and the other 50% will display `(Fallback) Hello Duke`. The salutation application's /salutation endpont has a random pause that will trigger a timeout (via MicroProfile's @Timeout annotation).

3. Test live reload.  In a third window or text editor,
    * Edit quarkus-springmp-person/src/main/java/org/acme/springmp/PersonSpringMPService.java and change `@Timeout(500)` to a different value between 1 and 999. In fact, change it to 750.
    * Save the file.
    * Refresh the http://localhost:8080/person/greet/1 URL multiple times and notice the change in how many times the `Fallback (Hello Duke`) message appears. It should be roughly 25% of the time insteadof 50% of the time.
    * Note: Quarkus live reloaded the change! Also note that the pause time is printed to the salutation terminal window.

## Run applications as uberjars and native binaries

1. **Start PostgreSQL** When not running in dev or test mode, quarkus assumes production mode. This applies all properties prefixed with %prod in application.properties as well as properties with no prefix. The person application has configured PostgreSQL as the 'production' database. Run:
   * `docker pull postgres:11.5`
   * `docker run --detach --name postgres -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=quarkus -p 5432:5432 postgres`
2. **Build as a jar file** In the second terminal window:
   * Stop the person application (CTRL-C). 
   * Run `mvn clean package`
   * Run `java -jar target/person-runner.jar`. The application should start up in 2 seconds or less.
   * As before, test the application by using the browser urls (http://localhost:8080/person and http://localhost:8080/person/greet/1).
3. **Build as a native binary** In the second terminal window:
   * Stop the person application (CTRL-C). 
   * Run `mvn package -Pnative`. Note: Compiling a native binary can take 2.5+ minutes or longer (possibly with compilation warnings) and use over 2GB of ram. Luckily, compiling is a one-time event!
   * Run `target/person-runner`. Note the startup time - roughly .050 milliseconds!
   * As before, test the application by using the browser urls (http://localhost:8080/person and http://localhost:8080/person/greet/1).
