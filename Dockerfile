FROM openjdk:17-oracle

WORKDIR /app

COPY target/country-0.0.1-SNAPSHOT.jar country-service.jar

CMD ["java","-jar","country-service.jar"]