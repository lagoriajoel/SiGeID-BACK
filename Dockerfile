FROM openjdk:11.0.16-jre

WORKDIR /app

COPY ./target/informes-backend-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "informes-backend-0.0.1-SNAPSHOT.jar"]
