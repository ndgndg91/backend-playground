package com.ndgndg91.notification.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "push_history")
data class PushHistory(
    @Id
    val id: String? = null,
    val accountId: Long,
    val category: String,
    val type: String,
    val title: String,
    val body: String,
    val action: String,
    val createdAt: Long
)