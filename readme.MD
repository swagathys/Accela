
# Java Code Test

Simple Java Application with Database access. Application provides Rest APIs for CRUD operations on Person and Address. Swagger has been integrated with the App to make it easy with REST operations.

Application is using H2 as the database. 

A Person must exsist before adding address to Him.
A Person can be deleted only after deleting Addresses assigned to him.

## To RUN

Execute below from the Command line. Java 1.8 and Maven is necessary to run this application.

```bash
mvn spring-boot:run
```

## Usage

```rest
http://localhost:8080/swagger-ui.html
```
