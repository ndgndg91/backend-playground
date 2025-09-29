.PHONY: test build-jar build-docker run-docker, run-mongo

# 프로젝트 이름을 정의합니다. 이 이름은 JAR 파일과 Docker 이미지의 이름으로 사용됩니다.
MODULE_NAME ?= spring-demo


# 테스트를 실행하는 커맨드입니다.
test:
	./gradlew clean $(MODULE_NAME):test

# JAR 파일을 빌드하는 커맨드입니다.
build-jar:
	./gradlew clean $(MODULE_NAME):bootJar

# Docker 이미지를 빌드하는 커맨드입니다.
build-docker: build-jar
	cd $(MODULE_NAME) && docker build -t $(MODULE_NAME) .

# Docker 이미지를 실행하는 커맨드입니다.
run-docker:
	docker run -p 8080:8080 $(MODULE_NAME)

run-mongo:
	docker run --name ndg-mongo -v ~/data:/data/db -d -p 27017:27017 mongo