# AngularJsonWebTokenInSpring
Example app to connect angular by JWT with rest-spring service in other machine

This example contain two directories:
1. Front: Angular Application
2. Back: Rest Services with springboot

The server offer a simple bussines service that can add two numbers but it's protected.
The server offer other service to create a JWT (Json Web Token) that allow to invoke the bussines service.

Start the server:
```bash
user@localhost:~/workspace/Back$ mvn spring-boot:run
```

The back can be tested using Postman but a front application with angular has been created to test it.

Start the angular with:
```bash
user@localhost:~/workspace/Front$ npm start
```

This is a capture of the application:
![Angular Example App](/img/angularApp.png)



