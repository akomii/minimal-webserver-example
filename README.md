## minimal-webserver-example ![Java 17](https://img.shields.io/badge/Java-17-green) ![Jakarta EE9](https://img.shields.io/badge/Jakarta-EE9-blue) ![Spring Boot 3.0.5](https://img.shields.io/badge/Spring--Boot-3.0.5-green)

A minimalist REST and WebSocket server example using Jetty and Jersey.

### Modules

- **Parent**: Contains configuration and version variables.
- **Server**: Implements a server with basic REST endpoints and WebSocket using Jakarta API.
- **Spring**: Spring Boot wrapper for the server module. It allows deploying the application to a pre-configured server using `mvn spring-boot:run`.

### Deployment

#### Prerequisites

1. [Java Development Kit (JDK) 17](https://jdk.java.net/17/) - Download and install the JDK.
2. [Apache Maven](https://maven.apache.org/download.cgi) - Download and install the Maven build tool.

#### Deployment Steps

1. **Download the project**: Obtain the project folder. This folder should contain the `pom.xml` file and the project's source code.


2. **Open the terminal/command prompt**: Navigate to the project folder `minimal-webserver-example` using the terminal (Mac/Linux) or command prompt (Windows).


3. **Build the project**: Run the following command to build and package the project: `mvn clean package`. This command will download the required dependencies, compile the project, and package it into an executable JAR file.
   The final JAR file will be located in the directory `spring/target`, and its name will be `spring-1.0-SNAPSHOT.jar`.


4. **Run the application**: Execute the following command: `java -jar spring/target/spring-1.0-SNAPSHOT.jar`. This will start the Spring Boot application, and it will be accessible locally.


5. **Access the application**: You can now access the application's REST endpoints and WebSocket using your preferred tools or web browser, connecting to `http://localhost:8080/...`.
