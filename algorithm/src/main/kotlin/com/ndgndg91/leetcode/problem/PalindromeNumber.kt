package com.ndgndg91.leetcode.problem

class PalindromeNumber {

    class Solution {
        fun isPalindrome(x: Int): Boolean {
            if (x < 0) return false

            val numString = x.toString()
            var left = 0
            var right = numString.lastIndex

            while (left < right) {
                if (numString[left] != numString[right]) {
                    return false
                }
                left++
                right--
            }

            return true
        }
    }
}

fun main() {
    val solution = PalindromeNumber.Solution()
    check(solution.isPalindrome(121))
    check(!solution.isPalindrome(-121))
    check(!solution.isPalindrome(10))
}
