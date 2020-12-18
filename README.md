# crime-portal-gateway-client
A client to exercise the Crime Portal Gateway.

This is implemented as a simple Spring Boot command line application. It will construct a SOAP request and send it to the crime portal gateway SOAP endpoint with necessary SOAP security headers. The payload of this SOAP request is the XML found in the project file art src/main/resources/requests/external-document-request.xml.

The intention is that initially the project will offer a simple means of testing our secured SOAP endpoints in dev and preprod namespaces. It could be extended to provide a means of load testing the endpoint.

# JKS

When the application runs it requires a java keystore (JKS) in order to sign and encrypt messages. This will be provided separately.

# Environment variables

| Syntax           | Description | Example |
| ---------------- | ----------- |---------
| soap-default-uri | The WS endpoint to call | http://localhost:8080/crime-portal-gateway/ws|
| keystore-password | Password used for the JKS | changeit |
| trusted-cert-alias-name | Alias for the trusted certificate in the JKS | server-public |
| private-key-alias-name | Password used for the JKS | client |

# Instructions

The application has pre-configured profiles - local, dev and preprod. 

## Pre-requisites
You will need 
* JDK at version 11 or higher
* Gradle at version 6.3 or higher

# Crime Portal Gateway

CPG exposes endpoints for health (/health) and WSDL (/crime-portal-gateway/ws/ExternalDocumentRequest.wsdl). These can be checked prior to running the test. The hosts are as follows 

* Dev       https://crime-portal-gateway-dev.apps.live-1.cloud-platform.service.justice.gov.uk/
* Preprod   https://crime-portal-gateway-preprod.apps.live-1.cloud-platform.service.justice.gov.uk/

# Running the client

Start the LOCAL spring profile. Communicates to the SOAP endpoint running at localhost, unencrypted by default.
```
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

Start the app with the CPG DEV spring profile. Communicates to the SOAP endpoint at https://crime-portal-gateway-dev.apps.live-1.cloud-platform.service.justice.gov.uk/crime-portal-gateway/ws/, with an unencrypted and unsigned message.
```
SPRING_PROFILES_ACTIVE=cpg-dev ./gradlew bootRun
```

Start the app with the CPG PRE-PROD profile. Communicates to the SOAP endpoint at https://crime-portal-gateway-preprod.apps.live-1.cloud-platform.service.justice.gov.uk/crime-portal-gateway/ws/, with an encrypted and signed message. The environment variables shown are samples and may be adjusted for the key pair being used.
```
SPRING_PROFILES_ACTIVE=cpg-preprod KEYSTORE_PASSWORD=changeit TRUSTED_CERT_ALIAS_NAME=server-public PRIVATE_KEY_ALIAS_NAME=client ./gradlew bootRun
```


