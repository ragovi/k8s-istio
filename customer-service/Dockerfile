FROM openjdk:alpine
MAINTAINER Raúl González <raul.gzv@gmail.com>
ADD target/customer-service.jar customer-service.jar
ENTRYPOINT ["java", "-jar", "/customer-service.jar"]
EXPOSE 3333