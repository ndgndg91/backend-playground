package com.ndgndg91.project

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class ProjectTemplateApplicationKtTest: AbstractIntegrationTest() {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun contextLoads() {
        logger.info("detekt 방지")
    }
}