package com.ndgndg91.pointapi.entity

/**
 * 포인트 풀 상태를 나타내는 Enum
 * 1: 진행 중, 2: 종료
 */
enum class PointPoolStatus(val code: Int) {
    ACTIVE(1),
    CLOSED(2);

    companion object {
        fun fromCode(code: Int): PointPoolStatus {
            return entries.find { it.code == code }
                ?: throw IllegalArgumentException("Unknown PointPoolStatus code: $code")
        }
    }
}