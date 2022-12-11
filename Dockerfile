FROM gradle:7.6.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle
WORKDIR /home/gradle
RUN gradle bootJar

FROM amazoncorretto:17-alpine
COPY --from=build /home/gradle/build/libs/*.jar ./app.jar
ENTRYPOINT [ "java", "-jar", "./app.jar" ]
