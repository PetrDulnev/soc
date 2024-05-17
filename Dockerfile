FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle ./build /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon


FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /home/gradle/src/libs/ahub-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar
ENTRYPOINT ["java","-Xdebug","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9251", "-jar", "/app/spring-boot-application.jar"]