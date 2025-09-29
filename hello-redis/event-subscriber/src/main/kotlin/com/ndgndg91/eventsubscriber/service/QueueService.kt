package com.ndgndg91.eventsubscriber.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Duration


@Service
class QueueService(
    val template: StringRedisTemplate
) {
    companion object {
        private const val JOB_KEY = "QUEUE:JOB"
        private val mapper = jacksonObjectMapper()
        private val logger = LoggerFactory.getLogger(QueueService::class.java)
    }


    @Scheduled(fixedRate = 1000)
    fun process() {
        logger.info("run ${System.currentTimeMillis()}")
        template.opsForList().leftPop(JOB_KEY, Duration.ofMillis(500))?.let {
            val job = mapper.readValue(it, Job::class.java)
            val result = JobResult(job, (job.start..job.end).sum().toLong())
            template.opsForValue().set("$JOB_KEY:${job.id}", mapper.writeValueAsString(result), Duration.ofMinutes(10))
        }
    }
}