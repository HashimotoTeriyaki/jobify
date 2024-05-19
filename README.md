# Jobify

Job offer posting web app based on microservices architecture.


## Quick start guide

Create modules using intellij's drop-down menu :

```bash
 File -> New -> Module from Existing Sources... -> pom.xml
```
Navigate to docker directory in job-offer-service and run command :

```bash
 docker compose up -d
```
Generate necessary classes using the following command in job-offer-service directory :

```bash
 mvn clean install -DskipTests
```
Finally run both job-offer-service and api-gateway
