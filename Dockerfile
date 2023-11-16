FROM maven:3-amazoncorretto-17 AS buildStep
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM amazoncorretto:17-alpine AS imageStep
WORKDIR /app
COPY --from=buildStep /app/target/hstflly.jar .
EXPOSE 8080
CMD ["java", "-jar", "hstflly.jar"]

#CMD tail -f /dev/null