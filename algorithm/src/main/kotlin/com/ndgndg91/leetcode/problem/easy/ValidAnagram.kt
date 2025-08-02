package com.ndgndg91.leetcode.problem.easy

class ValidAnagram {
    class Solution {
        fun isAnagram(s: String, t: String): Boolean {
            val sMap = mutableMapOf<Char, Int>()
            val tMap = mutableMapOf<Char, Int>()

            for (c in s) {
                sMap.put(c, sMap.getOrDefault(c, 0) + 1)
            }

            for (c in t) {
                tMap.put(c, tMap.getOrDefault(c, 0) + 1)
            }

            return sMap == tMap
        }
    }
}

fun main() {
    val solution = ValidAnagram.Solution()
    check(solution.isAnagram("anagram", "nagaram"))
    check(!solution.isAnagram("car", "rat"))
    check(!solution.isAnagram("a", "abb"))
}