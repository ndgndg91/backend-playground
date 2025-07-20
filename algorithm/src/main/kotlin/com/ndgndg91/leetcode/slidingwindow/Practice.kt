package com.ndgndg91.leetcode.slidingwindow

import kotlin.math.max

fun longestValidLength(array: Array<Int>, k: Int): Int {
    var left = 0
    var right = 0
    var curr = 0
    var answer = 0
    while (right <= array.lastIndex) {
        curr += array[right]
        while (curr > k) {
            curr -= array[left]
            left++
        }
        answer = max(answer, right - left + 1)
        right++
    }

    return answer
}

fun longestOne(n: Int): Int {
    val binaryString = Integer.toBinaryString(n)
    var left = 0
    var right = 0
    var zeroCnt = 0
    var answer = 0

    //11011
    while (right <= binaryString.lastIndex) {
        if (binaryString[right] == '0') {
            zeroCnt++
        }

        while (zeroCnt >= 1) {
            if (binaryString[left] == '0') {
                zeroCnt--
            }
            left++
        }

        answer = max(answer, right - left + 1)
        right++
    }

    return answer
}

fun longestOneIncludingOnceZero(n: Int): Int {
    val binaryString = Integer.toBinaryString(n)
    var left = 0
    var right = 0
    var zeroCnt = 0
    var answer = 0

    while (right <= binaryString.lastIndex) {
        if (binaryString[right] == '0') {
            zeroCnt++
        }

        while (zeroCnt >= 2) {
            if (binaryString[left] == '0') {
                zeroCnt--
            }
            left++
        }

        answer = max(answer, right - left + 1)
        right++
    }

    return answer
}

fun numSubarrayProductLessThanK(array: Array<Int>, k: Int): Int {
    var left = 0
    var right = 0
    var curr = 1
    var answer = 0

    while (right <= array.lastIndex) {
        curr *= array[right]
        while (curr > k) {
            curr /= array[left]
            left++
        }

        answer = max(answer, right - left + 1)
        right++
    }

    return answer
}

fun findBiggestSumInSubarray(array: Array<Int>, k: Int): Int {
    var curr = 0
    for (i in 0 until k) {
        curr += array[i]
    }

    var answer = curr
    var index = k
    while (index <= array.lastIndex) {
        curr += array[index] - array[index - k]
        answer = max(answer, curr)
        index++
    }

    return answer
}

fun main() {
    check(longestValidLength(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9), 6) == 3)
    check(longestOne(31) == 5)
    check(longestOne(1) == 1)
    check(longestOne(2) == 1)
    check(longestOne(4) == 1)
    check(longestOne(8) == 1)
    check(longestOne(16) == 1)
    check(longestOne(32) == 1)
    check(longestOne(64) == 1)
    check(longestOne(128) == 1)
    check(longestOne(256) == 1)
    check(longestOne(512) == 1)
    check(longestOne(1023) == 10)
    check(longestOne(1024) == 1)

    check(longestOneIncludingOnceZero(2048) == 2)
    check(longestOneIncludingOnceZero(62) == 6)

    check(numSubarrayProductLessThanK(arrayOf(10, 5, 6, 2), 100) == 3)

    check(findBiggestSumInSubarray(arrayOf(18, -1, 4, 12, -7, -6, 40), 4) == 39)
}