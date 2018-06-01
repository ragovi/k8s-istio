FROM openjdk:alpine
MAINTAINER aúl González <raul.gzv@gmail.com>
ADD target/account-service.jar account-service.jar
ENTRYPOINT ["java", "-jar", "/account-service.jar"]
EXPOSE 2222