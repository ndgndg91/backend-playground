package com.ndgndg91.springgrpcdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringGrpcDemoApplication

fun main(args: Array<String>) {
    runApplication<SpringGrpcDemoApplication>(*args)
}
