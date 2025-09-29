FROM amazoncorretto:17
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} distributed-lock-example.jar
EXPOSE 8080
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "/app/distributed-lock-example.jar"]