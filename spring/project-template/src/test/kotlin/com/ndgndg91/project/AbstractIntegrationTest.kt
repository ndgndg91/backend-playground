package com.ndgndg91.project

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(properties = ["spring.config.location=classpath:/local/application.yaml"])
@ActiveProfiles("test")
abstract class AbstractIntegrationTest {

    companion object {
        private val logger = LoggerFactory.getLogger(AbstractIntegrationTest::class.java)

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            // Redis 속성
            registry.add("infra.redis.host", TestContainers.REDIS_CONTAINER::getHost)
            registry.add("infra.redis.port", TestContainers.REDIS_CONTAINER::getFirstMappedPort)

            // MongoDB 속성
            registry.add("infra.mongodb.uri", TestContainers.MONGODB_CONTAINER::getReplicaSetUrl)
            registry.add("infra.mongodb.ssl.enabled") { "false" }

            logger.info("kafka bootstrap server {}", TestContainers.KAFKA_CONTAINER.bootstrapServers)

            // Kafka Producer 속성
            registry.add("infra.kafka.producer.bootstrap-server", TestContainers.KAFKA_CONTAINER::getBootstrapServers)
            registry.add("infra.kafka.producer.security-protocol") { "PLAINTEXT" }

            registry.add("infra.kafka.consumer.bootstrap-server", TestContainers.KAFKA_CONTAINER::getBootstrapServers)
            registry.add("infra.kafka.consumer.security-protocol") { "PLAINTEXT" }
            registry.add("infra.kafka.consumer.dlt.security-protocol") { "PLAINTEXT" }

            registry.add("infra.default-datasource.jdbc-url") { TestContainers.MYSQL_CONTAINER.jdbcUrl }
            registry.add("infra.default-datasource.username") { TestContainers.MYSQL_CONTAINER.username }
            registry.add("infra.default-datasource.password") { TestContainers.MYSQL_CONTAINER.password }
            registry.add("infra.default-datasource.driver-class-name") { TestContainers.MYSQL_CONTAINER.driverClassName }

            registry.add("infra.secondary-datasource.jdbc-url") { TestContainers.MYSQL_CONTAINER.jdbcUrl }
            registry.add("infra.secondary-datasource.username") { TestContainers.MYSQL_CONTAINER.username }
            registry.add("infra.secondary-datasource.password") { TestContainers.MYSQL_CONTAINER.password }
            registry.add("infra.secondary-datasource.driver-class-name") { TestContainers.MYSQL_CONTAINER.driverClassName }
        }
    }
}