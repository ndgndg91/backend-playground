package com.ndgndg91.springcommonbeans

import org.springframework.core.env.Environment

class ActiveProfileDetector(
    private val environment: Environment
) {
    fun isDev() = environment.activeProfiles.contains("dev")
    fun isProd() = environment.activeProfiles.contains("prod")
    fun isTest() = environment.activeProfiles.contains("test")
    fun isLocal() = environment.activeProfiles.contains("local")
    fun isQa() = environment.activeProfiles.contains("qa")
}