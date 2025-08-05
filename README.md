
# Spring Boot API Technical Assignment

This project is built on Spring Boot using MVC project structure in order to fulfill the requirement of Michael Pandu Nurseto's Technical Assignment.


## Authors

- [Michael Pandu Nurseto](https://www.github.com/mikeyangelo5)


## Requirements for Local Development & Runtime

- Java Development Kit 17+
- MySQL 8+
- Intellij Idea
- Postman (Testing)


## Complete API Documentation

[Postman documentation](https://documenter.getpostman.com/view/17953934/2sB3BBpBYE)


## Environment Variables

To run this project on your local machine please edit the following database configuration fields in the `application.properties` file inside the `src/main/resources` folder before running the service

`spring.datasource.url=jdbc:mysql://localhost:3306/{db-name}`

`spring.datasource.username={db-user}`

`spring.datasource.password={db-password}`
## Run Locally

- Clone the project

```bash
  git clone {git-url}
```

- Go to the project directory

```bash
  cd {folder-name}
```

- Import database

```bash
  cd database
  mysql -u {user} {db-name} < db.sql      #if password is not needed
  mysql -u {user} -p {db-name} < db.sql   #if password is needed
```

- Start the server

```bash
  mvn spring-boot:run
```

&emsp;&emsp;or

```bash
  mvn clean package
  java -jar target/demo-0.0.1-SNAPSHOT.jar
```
## Test the API

- Open the Postman Documentation
- Click "Run in Postman" button on top right
- Start testing the API Endpoints from Postman
