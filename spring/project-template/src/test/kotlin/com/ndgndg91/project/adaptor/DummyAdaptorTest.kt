package com.ndgndg91.project.adaptor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DummyAdaptorTest {
    @InjectMocks
    private lateinit var adaptor: DummyAdaptor


    @Nested
    @DisplayName("dummyA")
    inner class DummyA {
        @Test
        @DisplayName("case1")
        fun test_dummyA() {
            // given

            // when
            val result = kotlin.runCatching { adaptor.dummyA() }
                .onFailure { it.printStackTrace() }

            // then
            assertThat(result.isFailure).isTrue()
        }
    }

}