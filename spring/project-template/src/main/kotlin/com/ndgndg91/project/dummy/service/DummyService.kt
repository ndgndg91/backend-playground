package com.ndgndg91.project.dummy.service

import com.ndgndg91.project.dummy.service.dto.command.CreateDummyCommand
import org.springframework.stereotype.Service

@Service
class DummyService {

    @Suppress("UnusedParameter")
    fun findAll(page: Int, pageSize: Int): List<*> {
        throw NotImplementedError()
    }

    @Suppress("UnusedParameter")
    fun findById(id: Long): List<*> {
        throw NotImplementedError()
    }

    @Suppress("UnusedParameter")
    fun create(project: CreateDummyCommand): List<*> {
        throw NotImplementedError()
    }

    @Suppress("UnusedParameter")
    fun update(project: Any) {
        throw NotImplementedError()
    }

    @Suppress("UnusedParameter")
    fun deleteById(id: Long) {
        throw NotImplementedError()
    }
}