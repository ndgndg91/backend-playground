package com.ndgndg91.project.global.config.kafka

import com.ndgndg91.project.global.ext.toJsonByteArray
import io.cloudevents.CloudEventData

interface KafkaEventData: CloudEventData {
    override fun toBytes(): ByteArray {
        return this.toJsonByteArray()
    }
}