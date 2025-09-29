package com.ndgndg91.distributedlock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class DistributedLockApplication

fun main(args: Array<String>) {
    runApplication<DistributedLockApplication>(*args)
}
