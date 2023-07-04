FROM openjdk:17

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

COPY src ./src

EXPOSE ${PORT}

CMD ["./mvnw", "spring-boot:run"]