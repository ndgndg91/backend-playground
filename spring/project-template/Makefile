include Envfile

.PHONY: check
check:
		./gradlew clean check --stacktrace

.PHONY: run
run:
		./gradlew bootRun

.PHONY: dockerBuild
dockerBuild:
		./gradlew clean && ./gradlew assemble && docker build --no-cache . -t project-template

.PHONY: dockerRun
dockerRun:
		docker run --rm --platform linux/amd64 -p 8080:8080 --env-file="bin/environments" -t project-template

.PHONY: testCoverage
testCoverage:
		./gradlew clean testCoverage --stacktrace