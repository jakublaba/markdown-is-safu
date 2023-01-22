FROM gradle:jdk-alpine AS build
COPY --chown=gradle:gradle . /home/gradle
WORKDIR /home/gradle
# Generate RSA key pair for signing JWTs
RUN apk add openssl
RUN openssl genrsa -out keypair.pem 2048
RUN openssl rsa -in keypair.pem -pubout -out public.pem
RUN openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
RUN rm keypair.pem
RUN mkdir -p /home/gradle/src/main/resources/jwt
RUN mv /home/gradle/*.pem /home/gradle/src/main/resources/jwt/
# Build the jar
RUN gradle bootJar --no-daemon

FROM amazoncorretto:17-alpine
COPY --from=build /home/gradle/build/libs/*.jar ./app.jar
ENTRYPOINT [ "java", "-jar", "./app.jar" ]
