FROM amazoncorretto:21-alpine-jdk

ENV JAVA_MAJOR_VERSION=21 \
    JAVA_APP_DIR=/deployments \
    JAVA_APP_JAR=app.jar

# 애플리케이션의 JAR 파일 이름을 정의합니다.
ARG JAR_FILE=build/libs/*.jar

# JAR 파일을 이미지 내부로 복사합니다.
COPY ${JAR_FILE} ${JAVA_APP_DIR}/app.jar

ADD deployments /deployments/
RUN chmod 755 /deployments/run-java.sh

RUN apk --update --no-cache add bash procps && \
    rm -rf /opt/agent-bond

ARG USER=ndgndg91
ENV HOME /home/$USER

# install sudo as root
RUN apk add --update sudo

# add new user
RUN adduser -D $USER -u 1000 -h $HOME\
    && echo "$USER ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/$USER \
    && chmod 0440 /etc/sudoers.d/$USER

RUN echo "networkaddress.cache.ttl=60" >> /usr/lib/jvm/default-jvm/jre/lib/security/java.security

USER $USER
WORKDIR $HOME

CMD [ "/deployments/run-java.sh" ]