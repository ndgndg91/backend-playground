package com.ndgndg91.project.global.config.kafka

import com.ndgndg91.project.global.ext.toJson
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "infra.kafka.producer")
class KafkaProducerProperties {
    var bootstrapServer: String? = null
    var acks: String? = null
    var securityProtocol: String? = null
    override fun toString(): String {
        return this.toJson()
    }
}