#Build
FROM maven:3.9.9-amazoncorretto-21 AS build

VOLUME /root/gestitre
COPY . .
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests=true

#Runtime
FROM amazoncorretto:21

ARG ARG_APP_PROFILE=local
ARG ARG_APP_VERSION=1.0-SNAPSHOT
ARG ARG_DB_SERVER=missing_db_server
ARG ARG_DB_NAME=missing_db_name
ARG ARG_DB_USER=missing_db_user
ARG ARG_DB_PASSWORD=missing_db_password

WORKDIR /app
COPY --from=build /build/gestitre-bootstrap/target/quarkus-app/quarkus-run*.jar /app/


ENV ENV_APP_VERSION=${ARG_APP_VERSION}
ENV ENV_APP_PROFILE=local
ENV ENV_APP_PORT=8080
ENV ENV_DB_USER=dev
ENV ENV_DB_PASSWORD=hP5e~f34F:47\eti.Utb5
ENV ENV_DB_NAME=gestitre_db
ENV ENV_DB_SERVER=gestitre_postgresql
ENV ENV_DB_PORT=5432

EXPOSE ${ENV_APP_PORT}

CMD java -jar \
    -Dquarkus.profile=${ENV_APP_PROFILE} \
    -Dquarkus.datasource.username=${ENV_DB_USER} \
    -Dquarkus.datasource.password=${ENV_DB_PASSWORD} \
    -Dquarkus.datasource.url=jdbc:postgresql://${ENV_DB_SERVER}:${ENV_APP_PORT}/${ENV_DB_NAME} \
    quarkus-run-${ENV_APP_VERSION}.jar
