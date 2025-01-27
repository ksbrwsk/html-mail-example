## Simple Spring Boot app to send HTML emails using Mustache templates.

Build & run
-----------

**Prerequisites:**

* Java 21
* Apache Maven (https://maven.apache.org/)
* Docker (https://www.docker.com/)

Change your SMTP server settings in the  file:
```bash
./src/main/resources/application.yml
```

Start by setting up the Mailpit SMTP server in the `compose.yaml` file
by running the following command:

```bash
docker-compose up -d
```

Build with

```bash
mvn package
```

and run with

```bash
java -jar target/html-mail-example-1.0.0-SNAPSHOT.jar
```

or

```bash
mvn spring-boot:run
```

Point your browser to http://localhost:8025 to check to check your Mailpit inbox.

