package com.ndgndg91.project.dummy.controller

import com.ndgndg91.project.dummy.service.DummyService
import com.ndgndg91.project.global.exception.ErrorCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.given
import org.mockito.kotlin.never
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(
    properties = ["spring.config.location=classpath:/local/application.yaml"],
    controllers = [DummyGatewayController::class]
)
class DummyGatewayControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var service: DummyService

    @Nested
    @DisplayName("목록_조회")
    inner class FindAllTest {
        private val uri = "/dummy/items"

        @Test
        @DisplayName("성공")
        fun test_findDummyItems_2xx() {
            // given
            val page = 0
            val pageSize = 10
            given(service.findAll(page, pageSize)).willReturn(listOf("dummy", "dummy"))

            // when - then
            mockMvc.perform(
                MockMvcRequestBuilders.get(uri)
                    .queryParam("page", "0")
                    .queryParam("pageSize", "100")
            ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.responseCode").value(ErrorCode.SUCCESS.code))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.timestamp").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.requestId").isString)
        }

        @Test
        @DisplayName("실패_유효하지_않은_쿼리파람")
        fun test_findDummyItems_4xx() {
            // given - when
            val page = 0
            val pageSize = 10
            mockMvc.perform(
                MockMvcRequestBuilders.get(uri)
                    .queryParam("page", "-1")
                    .queryParam("pageSize", "100")
            ).andExpect(MockMvcResultMatchers.status().is4xxClientError)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").hasJsonPath())
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.meta.responseCode").value(ErrorCode.INVALID_PARAMETER.code)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.timestamp").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.requestId").isString)

            // then
            then(service).should(never()).findAll(page, pageSize)
        }
    }

    @Nested
    @DisplayName("필드간_의존성_validation_example")
    inner class DependenciesExampleTest {
        private val uri = "/dummy/items/depend-on"

        @ParameterizedTest
        @ValueSource(
            strings = [
                "{\n  \"a\": 100,\n  \"dependOnA\": \"POSITIVE\"\n}",
                "{\n  \"a\": -100,\n  \"dependOnA\": \"NEGATIVE\"\n}"
            ]
        )
        @DisplayName("정상")
        fun test_2xx(body: String) {
            // given - when - then
            mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.responseCode").value(ErrorCode.SUCCESS.code))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.timestamp").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.requestId").isString)
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "{\n  \"a\": -100,\n  \"dependOnA\": \"POSITIVE\"\n}",
                "{\n  \"a\": 100,\n  \"dependOnA\": \"NEGATIVE\"\n}"
            ]
        )
        @DisplayName("의존성_유효성_실패")
        fun test_4xx(body: String) {
            // given - when - then
            mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body)
            ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").hasJsonPath())
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.meta.responseCode").value(ErrorCode.INVALID_PARAMETER.code)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.timestamp").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta.requestId").isString)
        }
    }
}