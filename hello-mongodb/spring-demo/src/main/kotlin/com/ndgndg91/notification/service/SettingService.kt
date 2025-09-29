package com.ndgndg91.notification.service

import com.ndgndg91.notification.controller.dto.request.CreateSettingsRequest
import com.ndgndg91.notification.controller.dto.request.UpdateSettingsRequest
import com.ndgndg91.notification.domain.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.RuntimeException

@Service
class SettingService(
    private val repository: SettingsRepository
) {

    fun findByAccountId(accountId: Long): Settings {
        return repository.findByIdOrNull(accountId)
            ?: throw RuntimeException()
    }

    fun save(body: CreateSettingsRequest) {
        if (repository.existsById(body.accountId)) {
            throw RuntimeException()
        }

        repository.save(body.toSettings())
    }

    fun update(body: UpdateSettingsRequest) {
        if (!repository.existsById(body.accountId)) {
            throw RuntimeException()
        }

        repository.save(body.toSettings())
    }
}