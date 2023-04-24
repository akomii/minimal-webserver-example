## minimal-webserver-example ![Java 17](https://img.shields.io/badge/Java-17-green) ![Jakarta EE9](https://img.shields.io/badge/Jakarta-EE9-blue) ![Spring Boot 3.0.5](https://img.shields.io/badge/Spring--Boot-3.0.5-green)

A minimalist REST and WebSocket server example using Jetty and Jersey.

### Modules

- **Parent**: Contains configuration and version variables.
- **Server**: Implements a server with basic REST endpoints and WebSocket using Jakarta API.
- **Spring**: Spring Boot wrapper for the server module. It allows deploying the application to a pre-configured server using `mvn spring-boot:run`.