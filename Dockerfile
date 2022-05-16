FROM openjdk:latest
COPY ./job-application-service/build/libs/job-application-service-1.0-SNAPSHOT.jar jobapplicationtarget.jar
ENTRYPOINT ["java","-jar","/jobapplicationtarget.jar", "JobApplicationMain"]
