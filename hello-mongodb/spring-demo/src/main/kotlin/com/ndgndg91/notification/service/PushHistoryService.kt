package com.ndgndg91.notification.service

import com.ndgndg91.notification.controller.dto.request.CreatePushHistoryRequest
import com.ndgndg91.notification.domain.PushHistory
import com.ndgndg91.notification.domain.PushHistoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class PushHistoryService(
    private val repository: PushHistoryRepository
){

    fun create(body: CreatePushHistoryRequest): PushHistory {
        return repository.save(PushHistory(
            accountId = body.accountId,
            category = body.category,
            type = body.type,
            title = body.title,
            body = body.body,
            action = body.action,
            createdAt = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        ))
    }

    fun findById(id: String): PushHistory {
        return repository.findByIdOrNull(id) ?: throw RuntimeException()
    }
}