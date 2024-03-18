FROM openjdk:21
ENV PORT 8085
EXPOSE 8085
ADD /target/DriverService-0.0.1-SNAPSHOT.jar driver-service.jar
ENTRYPOINT ["java", "-jar", "driver-service.jar"]