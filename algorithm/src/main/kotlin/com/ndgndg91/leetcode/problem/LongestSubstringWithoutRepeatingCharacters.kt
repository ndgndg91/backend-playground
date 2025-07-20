package com.ndgndg91.leetcode.problem

import kotlin.math.max

class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        var left = 0
        var right = 0
        var answer = 0
        val set = HashSet<Char>()

        while (right < s.length) {
            while (s[right] in set) {
                set.remove(s[left])
                left++
            }

            set.add(s[right])

            answer = max(answer, right - left + 1)
            right++
        }

        return answer
    }
}

fun main() {
    val solution = Solution()
    check(solution.lengthOfLongestSubstring("abcabcbb") == 3)
    check(solution.lengthOfLongestSubstring("bbbbb") == 1)
    check(solution.lengthOfLongestSubstring("pwwkew") == 3)
    check(solution.lengthOfLongestSubstring("dvdf") == 3)
}