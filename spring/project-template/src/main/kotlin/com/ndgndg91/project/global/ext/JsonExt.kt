package com.ndgndg91.project.global.ext

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val mapper: ObjectMapper by lazy {
    val objectMapper = jacksonObjectMapper()

    val formatter = DateTimeFormatter.ofPattern(ISO_8061_TIMESTAMP)
    val localDateTimeSerializer = LocalDateTimeSerializer(formatter)
    val localDateTimeDeserializer = LocalDateTimeDeserializer(formatter)

    val javaTimeModule = JavaTimeModule()
    javaTimeModule.addSerializer(LocalDateTime::class.java, localDateTimeSerializer)
    javaTimeModule.addDeserializer(LocalDateTime::class.java, localDateTimeDeserializer)

    objectMapper.registerModule(javaTimeModule)

    objectMapper
}

fun Any.toJson(): String {
    return mapper.writeValueAsString(this)
}

fun Any.toJsonByteArray(): ByteArray {
    return mapper.writeValueAsBytes(this)
}

inline fun <reified T : Any> String.toObject(): T {
    return mapper.readValue(this, T::class.java)
}

fun String.getValue(key: String): Any? {
    val a = mapper.readValue(this, HashMap::class.java)
    return a[key]
}
