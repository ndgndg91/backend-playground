package com.ndgndg91.project.global

import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ActiveProfileDetector(private val env: Environment) {

    fun isPrd(): Boolean {
        return activeProfile() in listOf("prd", "stage", "prod", "stg")
    }

    fun isDevOrQa(): Boolean {
        return activeProfile() in listOf("local", "dev", "qa")
    }

    fun isDev(): Boolean {
        return activeProfile() in listOf("local", "dev")
    }

    fun activeProfile(): String {
        return env.activeProfiles.firstOrNull()?: "local"
    }
}