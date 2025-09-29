package com.ndgndg91.leetcode.twopointer


fun printFromBothSide(arr: Array<Int>) {
    var left = 0
    var right = arr.lastIndex

    while (left <= right) {
        if (left == right) {
            println(arr[left])
        } else {
            println("left : ${arr[left]}")
            println("right : ${arr[right]}")
        }
        left++
        right--
    }
}

fun isPalindrome(s: String): Boolean {
    var left = 0
    var right = s.lastIndex

    while (left <= right) {
        if (s[left] != s[right]) {
            return false
        }
        left++
        right--
    }

    return true
}

fun twoSum(arr: Array<Int>, t: Int): Boolean {
    arr.sort()
    var left = 0
    var right = arr.lastIndex

    while (left < right) {
        if (arr[left] + arr[right] == t) {
            return true
        }
        else if (arr[left] + arr[right] < t) {
            left++
        } else {
            right--
        }
    }

    return false
}

fun main() {
    printFromBothSide(arrayOf(1,2,3))
    check(isPalindrome("aba"))
    check(!isPalindrome("abc"))
    check(isPalindrome("racecar"))
    check(twoSum(arrayOf(1,2,3), 4))
}