package com.ndgndg91.eventpublisher

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EventPublisherApplication

fun main(args: Array<String>) {
	runApplication<EventPublisherApplication>(*args)
}
