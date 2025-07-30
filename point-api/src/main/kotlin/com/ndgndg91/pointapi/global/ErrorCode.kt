package com.ndgndg91.pointapi.global

enum class ErrorCode(val code: Int) {
    UNKNOWN(-1),
    INVALID_PARAM(1);

    companion object {
        fun findByCode(code: Int): ErrorCode {
            return entries.firstOrNull { it.code == code } ?: UNKNOWN
        }
    }
}