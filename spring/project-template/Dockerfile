FROM alpine-corretto-jdk21-config-newrelic:latest

ARG BUILD_DIR=./build/libs
ARG BUILD_JAR=project-template.jar

# Application name should be upper case
ENV APPLICATION_NAME ${APPLICATION_NAME:-PROJECT-TEMPLATE}
ENV ACTIVE_PROFILE ${ACTIVE_PROFILE:-dev}
ENV SERVER_PORT ${SERVER_PORT:-8080}
ENV REST_CONFIG_HOST ${REST_CONFIG_HOST:-http://localhost}
ENV JAVA_INIT_MEM_RATIO ${JAVA_INIT_MEM_RATIO:-50}
ENV JAVA_APP_DIR ${JAVA_APP_DIR}

COPY ./bin/run-java-options /opt/run-java-options

COPY ${BUILD_DIR}/${BUILD_JAR} ${JAVA_APP_DIR}/app.jar

EXPOSE ${SERVER_PORT}