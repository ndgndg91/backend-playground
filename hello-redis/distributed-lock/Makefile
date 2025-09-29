start-compose:
	docker-compose up -d
kill-compose:
	docker-compose down
build-docker:
	./gradlew build
	docker build -t distributed-lock-example .
run-docker-containers:
	# 네트워크 및 호스트 확인하고 변경
	docker run -d -p 1010:8080 --network=distributed-lock_default --name=dle1 -e "SPRING_DATASOURCE_URL=jdbc:mysql://172.21.0.2:3306/race_condition" -e "SPRING_DATA_REDIS_HOST=172.21.0.3" -e "SPRING_DATA_REDIS_PORT=6379" distributed-lock-example
	docker run -d -p 2020:8080 --network=distributed-lock_default --name=dle2 -e "SPRING_DATASOURCE_URL=jdbc:mysql://172.21.0.2:3306/race_condition" -e "SPRING_DATA_REDIS_HOST=172.21.0.3" -e "SPRING_DATA_REDIS_PORT=6379" distributed-lock-example
	docker run -d -p 3030:8080 --network=distributed-lock_default --name=dle3 -e "SPRING_DATASOURCE_URL=jdbc:mysql://172.21.0.2:3306/race_condition" -e "SPRING_DATA_REDIS_HOST=172.21.0.3" -e "SPRING_DATA_REDIS_PORT=6379" distributed-lock-example
	docker run -d -p 4040:8080 --network=distributed-lock_default --name=dle4 -e "SPRING_DATASOURCE_URL=jdbc:mysql://172.21.0.2:3306/race_condition" -e "SPRING_DATA_REDIS_HOST=172.21.0.3" -e "SPRING_DATA_REDIS_PORT=6379" distributed-lock-example
kill-docker-containers:
	docker rm -f dle1
	docker rm -f dle2
	docker rm -f dle3
	docker rm -f dle4