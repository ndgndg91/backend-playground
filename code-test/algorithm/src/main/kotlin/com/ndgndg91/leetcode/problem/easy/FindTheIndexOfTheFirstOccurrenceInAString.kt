package com.ndgndg91.leetcode.problem.easy

class FindTheIndexOfTheFirstOccurrenceInAString {
    class Solution {
        fun strStr(haystack: String, needle: String): Int {
            return haystack.indexOf(needle)
        }
    }
}

fun main() {
    val solution = FindTheIndexOfTheFirstOccurrenceInAString.Solution()
    check(solution.strStr("sadbutsad", "sad") == 0)
}