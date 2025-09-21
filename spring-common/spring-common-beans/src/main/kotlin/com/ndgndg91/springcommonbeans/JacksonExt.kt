package com.ndgndg91.springcommonbeans

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val mapper: ObjectMapper = jacksonObjectMapper()
    .registerModule(JavaTimeModule())

fun <R> String?.toObj(clazz: Class<R>): R? {
    return this?.let {
        return mapper.readValue(it, clazz)
    }
}

fun Any.toJson(): String = mapper.writeValueAsString(this)