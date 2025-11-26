package com.ndgndg91.project

import com.redis.testcontainers.RedisContainer
import org.slf4j.LoggerFactory
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.utility.DockerImageName
import java.time.Duration

object TestContainers {
    private val logger = LoggerFactory.getLogger(TestContainers::class.java)
    val REDIS_CONTAINER: RedisContainer = RedisContainer(DockerImageName.parse("redis:7-alpine"))
        .withExposedPorts(6379)
        .withReuse(true)

    val MONGODB_CONTAINER: MongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:5.0"))
        .withStartupTimeout(Duration.ofMinutes(2))
        .withReuse(true)

    val KAFKA_CONTAINER: KafkaContainer = KafkaContainer(DockerImageName.parse("apache/kafka:3.8.0"))
        .withReuse(true)

    val MYSQL_CONTAINER: MySQLContainer<*> = MySQLContainer(DockerImageName.parse("mysql:5.7"))
            .withStartupTimeout(Duration.ofMinutes(2))

    init {
        // 재사용 설정 활성화
        System.setProperty("testcontainers.reuse.enable", "true")

        // 컨테이너 시작
        REDIS_CONTAINER.start()
        MONGODB_CONTAINER.start()
        KAFKA_CONTAINER.start()
        MYSQL_CONTAINER.start()

        logger.info("All test containers started successfully")
    }
}