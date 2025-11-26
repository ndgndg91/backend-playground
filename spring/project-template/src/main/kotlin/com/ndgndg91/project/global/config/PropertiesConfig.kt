package com.ndgndg91.project.global.config

import com.ndgndg91.project.global.config.datasource.DefaultDataSourceConfig
import com.ndgndg91.project.global.config.datasource.SecondaryDataSourceConfig
import com.ndgndg91.project.global.config.documentdb.MongoDBProperty
import com.ndgndg91.project.global.config.kafka.KafkaConsumerProperties
import com.ndgndg91.project.global.config.kafka.KafkaProducerProperties
import com.ndgndg91.project.global.config.redis.RedisConfig
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    DefaultDataSourceConfig.DefaultDataSourceProperty::class,
    SecondaryDataSourceConfig.SecondaryDataSourceProperty::class,
    RedisConfig.RedisProperty::class,
    MongoDBProperty::class,
    KafkaProducerProperties::class,
    KafkaConsumerProperties::class
)
class PropertiesConfig