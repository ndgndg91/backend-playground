package com.ndgndg91.project.dummy.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ndgndg91.project.dummy.event.DummyEvent
import com.ndgndg91.project.dummy.event.TEMPLATE_EVENT
import com.ndgndg91.project.global.config.kafka.KafkaConfig.Companion.DEFAULT_KAFKA_LISTENER_CONTAINER_FACTORY
import com.ndgndg91.project.global.config.kafka.KafkaEventMessage
import io.cloudevents.CloudEvent
import io.cloudevents.core.CloudEventUtils
import io.cloudevents.jackson.PojoCloudEventDataMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaService(
    private val queueTemplate: KafkaTemplate<String, CloudEvent>
) {

    private val logger = LoggerFactory.getLogger(KafkaService::class.java)

    fun sendKafkaMessage() {
        queueTemplate.send(
            TEMPLATE_EVENT,
            KafkaEventMessage(
                source = "/template/event",
                subject = TEMPLATE_EVENT,
                type = DummyEvent::class.java.name,
                data = DummyEvent(
                    accountId = 1L,
                    username = "test"
                )
            )
        )
    }


    @KafkaHandler
    @KafkaListener(
        topics = [TEMPLATE_EVENT],
        containerFactory = DEFAULT_KAFKA_LISTENER_CONTAINER_FACTORY
    )
    fun consumeKafkaMessage(message: CloudEvent) {
        val data = CloudEventUtils.mapData(
            message,
            PojoCloudEventDataMapper.from(jacksonObjectMapper(), DummyEvent::class.java)
        )?.value!!
        logger.debug("accountId: {}, username: {}", data.accountId, data.username)
    }
}