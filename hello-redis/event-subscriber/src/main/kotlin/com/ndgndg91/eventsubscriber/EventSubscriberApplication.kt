package com.ndgndg91.eventsubscriber

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class EventSubscriberApplication

fun main(args: Array<String>) {
    runApplication<EventSubscriberApplication>(*args)
}
