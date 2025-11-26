package com.ndgndg91.project.global.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import com.fasterxml.jackson.core.json.JsonWriteFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import org.springframework.boot.logging.structured.StructuredLogFormatter
import java.util.function.Function
import kotlin.collections.any
import kotlin.collections.first
import kotlin.collections.joinToString
import kotlin.collections.plus
import kotlin.collections.set
import kotlin.to


class JsonStructuredLog : StructuredLogFormatter<ILoggingEvent> {
    companion object {
        private val objectMapper = JsonMapper.builder()
            .configure(JsonWriteFeature.ESCAPE_NON_ASCII, false)
            .build()

        var extractor: Function<ILoggingEvent, Map<String, Any?>> = Function { event ->
            try {
                if (event.argumentArray != null && event.argumentArray.any { arg -> arg is AccessLog }) {
                    val accessLog = event.argumentArray.first { it is AccessLog } as AccessLog
                    accessLog.toMap()
                } else {
                    val mutableMap = mutableMapOf<String, Any?>()
                    mutableMap["message"] = event.formattedMessage
                    if (event.throwableProxy != null) {
                        event.throwableProxy.suppressed
                        mutableMap["throwable_class_name"] = event.throwableProxy.className
                        mutableMap["throwable_message"] = event.throwableProxy.message
                        mutableMap["throwable_stack_trace"] = event.throwableProxy.stackTraceElementProxyArray.joinToString("\n") { it.stackTraceElement.toString() }
                    }
                    mutableMap
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mapOf("message" to event.formattedMessage)
            }
        }

        private fun createLogMap(event: ILoggingEvent): Map<String, Any?> {
            val baseMap = mapOf(
                "level" to event.level.levelStr,
                "timestamp" to event.timeStamp,
                "thread_name" to event.threadName,
                "logger" to event.loggerName,
            )
            return baseMap + extractor.apply(event)
        }
    }

    override fun format(event: ILoggingEvent): String {
        val logMap = createLogMap(event)
        return objectMapper.writeValueAsString(logMap) + "\n"
    }
}