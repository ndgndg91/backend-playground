package com.ndgndg91.project.dummy.event

import com.ndgndg91.project.global.config.kafka.KafkaEventData


data class DummyEvent(
    val accountId: Long?,
    val username: String?
) : KafkaEventData