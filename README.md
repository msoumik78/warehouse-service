# Functionality (Warehouse data collector service)
This repo contains a spring-boot app which listens to UDP messages at 2 designated ports for temperature and humidity
sensor data respectively. Important thing is that this app needs to be run alongside the central-monitoring-app.


# Technical details and Pre-requisites
- Written using Spring Boot framework
- Needs to have the Docker desktop running with Nats server running as a docker container
- Also needs to have the other app (central-monitoring-app) up and running


# How to run locally
- First run the nats server on docker using this command :
  ```kotlin
    docker run -p 4222:4222 -ti nats:latest
  ```
- Then clone the main branch. Ensure that you have at least JDK17 and Maven3.X available. Build the project using the command :
  ```kotlin
    mvn clean package -DskipTests
  ```
- Now run the app using the below command :
  ```kotlin
    java -jar target/warehouse-service-0.0.1-SNAPSHOT.jar
  ```
- Now send a UDP message using the command :
  ```kotlin
     nc -u localhost 3344
  ```
  and the message should be like this json format
  ```kotlin
      {"sensor_id" : "1", "value": "30"}
  ```
- You can see the below message in the log
  ```kotlin
    Temp data received = 30
  ```



# How to run the integration tests
- First run the nats server on docker using this command : docker run -p 4222:4222 -ti nats:latest
- Run the class WarehouseServiceApplicationTests
