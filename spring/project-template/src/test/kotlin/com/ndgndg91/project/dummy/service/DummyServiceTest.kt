package com.ndgndg91.project.dummy.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DummyServiceTest {
    @InjectMocks
    private lateinit var service: DummyService

    @Nested
    @DisplayName("목록_조회")
    inner class FindAllTest {
        @Test
        fun test_findAll() {
            // given
            val page = 0
            val pageSize = 10

            // when
            val result = kotlin.runCatching { service.findAll(page, pageSize) }
                .onFailure { it.printStackTrace() }

            // then
            assertThat(result.isFailure).isTrue()
        }
    }
}