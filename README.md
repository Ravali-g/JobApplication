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

```docker build . -t job-application-docker -f ./Dockerfile```

### **Run the project:**

```docker run -it -d --rm --network mydockernetwork --hostname jobapplication.local -p 8084:8005 job-application-docker:latest```


