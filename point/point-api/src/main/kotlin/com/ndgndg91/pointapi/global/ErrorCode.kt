package com.ndgndg91.pointapi.global

enum class ErrorCode(val code: Int) {
    UNKNOWN(-1),
    INVALID_PARAM(1),

    NO_FOUND_POINT_POOL(10),
    NO_GRANT_PERIOD(11),
    CLOSED_POOL_STATUS(12),

    ;

    companion object {
        fun findByCode(code: Int): ErrorCode {
            return entries.firstOrNull { it.code == code } ?: UNKNOWN
        }
    }
}