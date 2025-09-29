package com.ndgndg91.leetcode.problem.easy

import kotlin.math.min

class LongestCommonPrefix {
    class Solution {
        fun longestCommonPrefix(strs: Array<String>): String {
            strs.sort()
            val builder = StringBuilder()
            val first = strs.first()
            val last = strs.last()

            for (i in 0 .. min(first.lastIndex, last.lastIndex)) {
                if (first[i] != last[i]) {
                    return builder.toString()
                }

                builder.append(first[i])
            }

            return builder.toString()
        }

        fun longestCommonPrefixOld(strs: Array<String>): String {
            if (strs.isEmpty()) return ""

            var prefix = strs.first()
            for (i in 1 .. strs.lastIndex) {
                if (strs[i].isEmpty()) {
                    return ""
                }

                var right = strs[i].length
                while (right > 0) {
                    val substring = strs[i].substring(0, right)
                    if (prefix.startsWith(substring)) {
                        prefix = substring
                        break
                    }

                    if (substring.length == 1) {
                        return ""
                    }

                    right--
                }
            }

            return prefix
        }
    }
}

fun main() {
    val solution = LongestCommonPrefix.Solution()
    check(solution.longestCommonPrefix(arrayOf("flower","flow","flight")) == "fl")
    check(solution.longestCommonPrefix(arrayOf("dog","racecar","car")) == "")
}