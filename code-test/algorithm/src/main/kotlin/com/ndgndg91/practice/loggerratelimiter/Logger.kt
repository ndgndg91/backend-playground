package com.ndgndg91.practice.loggerratelimiter

class Logger {
    private val limiter = HashMap<String, Int>()

    fun shouldPrintMessage(timestamp: Int, message: String): Boolean {
        if (timestamp - limiter.getOrDefault(message, -100) < 10) {
            return false
        }

        limiter[message] = timestamp
        return true
    }

    fun test() {
        val logger = Logger()
        check(logger.shouldPrintMessage(1, "foo")) // return true, next allowed timestamp for "foo" is 1 + 10 = 11
        check(logger.shouldPrintMessage(2, "bar")) // return true, next allowed timestamp for "bar" is 2 + 10 = 12
        check(!logger.shouldPrintMessage(3, "foo")) // 3 < 11, return false
        check(!logger.shouldPrintMessage(8, "bar")) // 8 < 12, return false
        check(!logger.shouldPrintMessage(10, "foo")) // 10 < 11, return false
        check(logger.shouldPrintMessage(11, "foo")) // 11 >= 11, return true, next allowed timestamp for "foo" is 11 + 10 = 21
    }
}