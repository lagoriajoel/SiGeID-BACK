FROM openjdk:11.0.16-jre

WORKDIR /app

COPY ./target/informes-backend-0.0.1-SNAPSHOT.jar /app
COPY ./target/classes/reportPDF1.jasper /app/target/classes/
COPY ./target/classes/logoCPE.png /app/target/classes/
COPY ./target/classes/reportPDF.jrxml /app/target/classes/
COPY ./target/classes/fonts /app/target/classes/



EXPOSE 8001

ENTRYPOINT ["java", "-jar", "informes-backend-0.0.1-SNAPSHOT.jar"]
