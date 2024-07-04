# Jobify

Job offer posting web app based on microservices architecture.


## Quick start guide
Set up environment variables, as well as other e-mail properties in notification service application.yml file
according to your SMTP provider :
```bash
SMTP_PASSWORD="smtp password"
SMTP_USERNAME="smtp username"
```

Create modules using intellij's drop-down menu :

```bash
 File -> New -> Module from Existing Sources... -> pom.xml
```
Navigate to docker directory and run command :

```bash
 docker compose up -d
```
Generate necessary classes using the following command in job-offer-service directory :

```bash
 mvn clean install -DskipTests
```
Finally run both job-offer-service and api-gateway
