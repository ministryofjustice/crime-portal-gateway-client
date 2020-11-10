# crime-portal-gateway-client
A client to test the crime portal gateway.

This is implemented as a simple Spring Boot command line application. It will construct a hardcoded request and send it to the crime portal gateway SOAP endpoint with necessary SOAP security headers. 

The intention is that initially the project will offer a simple means of testing our secured SOAP endpoints in dev, preprod and prod namespaces. It could be extended to provide a means of load testing the endpoint.

# Instructions

The application has pre-configured profiles - local and dev. 

Start the app with the LOCAL profile. Communicates to the SOAP endpoint running at localhost.
```
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

Start the app with the DEV profile. 
```
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```

# JKS

The application runs it requires a keystore (JKS). TBD 

# Environment variables



| Syntax           | Description | Example |
| ---------------- | ----------- |---------
| soap-default-uri | The WS endpoint to call | http://localhost:8080/crime-portal-gateway/ws|
| keystore-password | Password used for the JKS | changeit |
| cert-entry-alias-name | Alias for the trusted certicate in the JKS | server-public |
| private-key-alias-name | Password used for the JKS | client |


