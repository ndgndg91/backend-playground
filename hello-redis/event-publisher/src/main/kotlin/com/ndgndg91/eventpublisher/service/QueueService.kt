package com.ndgndg91.eventpublisher.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class QueueService(
    val template: StringRedisTemplate
) {
    companion object {
        private const val JOB_KEY = "QUEUE:JOB"
        private val mapper = jacksonObjectMapper()
    }
    fun queue(start: Int, end: Int): String {
        val id = UUID.randomUUID().toString()
        template.opsForList().leftPush(
            JOB_KEY,
            mapper.writeValueAsString(Job(id, start, end))
        )
        return id
    }

    fun findJob(id: String): JobResult? {
        return template.opsForValue().get("$JOB_KEY:$id")?.let {
            mapper.readValue(it, JobResult::class.java)
        }
    }
}