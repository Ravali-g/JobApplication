# Welcome to Job Application!

### **Overview:**

This project provides an API to perform all CRUD oprations related to job applications on a career page.

### **Highlights:**

- Fully featured API including GET, POST, PATCH, PUT and DELETE
- Data is extracted via JPA
- Multi-module project
- 1 complete and runnable application
- Property mapping usages
- Docker configuration
- Asynchronous messaging - RabbitMQ



### **Technologies Used:**

- Spring Boot
- JPA
- Docker
- MySQL
- RabbitMQ




| **Module**                |  **Description**                    |
| ----------------------    |:-----------------------------------:| 
| job-application-contract  |  Describes the model contracts used |  
| job-application-service   | implementation and business logic   |                                   


### **Build the project:**

To generate build jar artifacts

```gradle clean build```

To build the docker image

```docker build . -t job-application-docker -f ./Dockerfile```

### **Run the project in docker:**

Dependencies: (RabbitMQ, MySQL)

```docker create network mydockernetwork```

```docker run -it -d --network mydockernetwork --hostname rabbimq.local --rm -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management```

```docker run -itd --network mydockernetwork --hostname mysql.local -it mysql/mysql-server:latest```

```docker run -it -d --rm --network mydockernetwork --hostname jobapplication.local -p 8084:8005 job-application-docker:latest```
