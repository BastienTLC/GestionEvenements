FROM amazoncorretto:21-alpine-jdk
RUN apk add --no-cache maven
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package spring-boot:repackage
RUN ls -la target
#COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "./target/gestionLieu-0.0.1-SNAPSHOT.jar"]
