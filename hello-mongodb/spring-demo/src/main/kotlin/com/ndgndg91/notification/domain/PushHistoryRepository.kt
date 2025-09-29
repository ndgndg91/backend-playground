package com.ndgndg91.notification.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface PushHistoryRepository: MongoRepository<PushHistory, String>