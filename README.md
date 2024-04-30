# Robot control app
Short description
-
REST-application on Java for managing robots.

Used frameworks and libraries:
- Spring Boot
- Spring Security
- Bucket4j
- Lombok
- JJWT
- Swagger
- MongoDB database

Brief description of the classes
-
The root package of the `com.microservice.robot` project contains the following classes:
- `SecurityApplication` - class, which contains the entry point to the application;

The package `config` contains the following class:
- `ApplicationAuditAware` - determine the current user interacting with the application;
- `ApplicationConfig` - configurator for delayed functions;
- `JwtAuthenticationFilter` - authentication filter config;
- `JwtService` - configuration to interact with jwt token;
- `LogoutService` - configuration to interact with jwt token;
- `SecurityConfiguration` - security configuration;

The package `controller` contains the following classes:
- `AuthenticationController` - provides endpoints for user registration and authentication;
- `RobotController` - provides endpoints for user to control robots;

The package `data` contains the following classes:
- `RefreshToken` - provides a structured representation of refresh tokens stored in a MongoDB collection;
- `Robot` -  provides a structured representation of robots stored in a MongoDB collection;
- `RobotFeedback` - provides a structured representation of robot feedback stored in a MongoDB collection;
- `Status` -  provides a structured representation of status is part of robot stored in a MongoDB collection;
- `User` -  provides a structured representation of administrator stored in a MongoDB collection;

The package `percictance` contains the following classes:
- `RefreshTokenRepository` - logic required to interact with the MongoDB database for managing refresh tokens;
- `RobotFeedbackRepository` -  logic required to interact with the MongoDB database for managing robot feedback;
- `RobotRepository` - logic required to interact with the MongoDB database for managing robots;
- `UserRepository` -  logic required to interact with the MongoDB database for managing users;

The package `request` contains the following classes:
- `AuthenticationRequest` - represents a request object used for admin authentication;
- `OnRobotRequest` -  represents a request object used for enable robot;
- `RegisterRequest` -  represents a request object used for admin registration;
- `RobotFeedbackRequest` -  represents a request object used for add robot feedback;

The package `response` contains the following classes:
- `AuthenticationResponse` - represents a response object used for admin authentication;
- `RobotStatusResponse` -  represents a response object used for get robot status;

The package `service` contains the following classes:
- `AuthenticationService` - business logic of user registration and authentication;
- `RobotService` -  business logic of robot control;

Description of the external programming interface
-
### Authenfication

To regiser, send a POST request to `/auth/sign-up/create-account`:

The params of the request must contain:
- `email=[mail@mail.com]` - admin email (Mandatory, can't be null);
- `password=[password]` - admin password  (Mandatory, can't be null, min length 8);
- `first_name=[name]` - admin first name  (Mandatory, can't be null, min length 2);
- `last_name=[lastName]` - admin last name  (Mandatory, can't be null, min length 2);

To login, send a POST request to `/auth/sign-in`:

The params of the request must contain:
- `email=[mail@mail.com]` - admin email (Mandatory, can't be null);
- `password=[password]` - admin password  (Mandatory, can't be null, min length 8);

### Robot control

To get all administrator robots, send a GET request to `/robot`;

To register new robot, send a POST request to `/robot`;

To enable robot, send a POST request to `/robot/on/{id}`;

The params of the request must contain:
- `task=[task]` - task for robot (Mandatory, can't be null);

To disable robot, send a POST request to `/robot/off/{id}`;

To get robot status, send a POST request to `/robot/status/{id}`;

To add robot feedback, send a POST request to `/feedback/{id}`;
The params of the request must contain:
- `feedback=[feedback]` - robot feedback (Mandatory, can't be null);
- `rating=[3]` - task for robot (Mandatory, can't be null, number from 1 to 5);

To get robot feedbacks, send a GET request to `/feedback/{id}`;

In cases where the any of the fields has an invalid format, or field `date` is missing, the code `400 Bad Request` is returned to the client.

Deploying on Docker commands
-
- `docker-compose up -d --build`