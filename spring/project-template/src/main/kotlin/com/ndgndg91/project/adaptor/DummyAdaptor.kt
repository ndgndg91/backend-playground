package com.ndgndg91.project.adaptor

import org.springframework.stereotype.Service

@Service
class DummyAdaptor {

    fun dummyA() {
        throw NotImplementedError()
    }

    fun dummyB() {
        throw NotImplementedError()
    }
}