FROM openjdk:21-jdk

WORKDIR /app

COPY . /app

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/build/flowershop-0.0.1-SNAPSHOT.jar"]