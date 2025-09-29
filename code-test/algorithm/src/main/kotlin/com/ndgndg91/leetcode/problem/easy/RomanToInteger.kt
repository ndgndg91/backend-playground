package com.ndgndg91.leetcode.problem.easy

class RomanToInteger {
    class Solution {
        fun romanToInt(s: String): Int {
            var index = 0
            var sum = 0
            while (index < s.length) {
                when {
                    ((s[index] == 'I' && index + 1 <= s.lastIndex && (s[index + 1] == 'V' || s[index + 1] == 'X'))) -> {
                        sum += convert("${s[index]}${s[index+1]}")
                        index += 2
                    }
                    (s[index] == 'X' && index + 1 <= s.lastIndex && (s[index + 1] == 'L' || s[index + 1] == 'C')) -> {
                        sum += convert("${s[index]}${s[index+1]}")
                        index += 2
                    }
                    (s[index] == 'C' && index + 1 <= s.lastIndex && (s[index + 1] == 'D' || s[index + 1] == 'M')) -> {
                        sum += convert("${s[index]}${s[index+1]}")
                        index += 2
                    }
                    else -> {
                        sum += convert("${s[index]}")
                        index++
                    }
                }
            }

            return sum
        }

        private fun convert(s: String): Int {
            return when (s) {
                "I" -> 1
                "IV" -> 4
                "V" -> 5
                "IX" -> 9
                "X" -> 10
                "XL" -> 40
                "L" -> 50
                "XC" -> 90
                "C" -> 100
                "CD" -> 400
                "D" -> 500
                "CM" -> 900
                "M" -> 1000
                else -> throw IllegalArgumentException("Unknown string: $s")
            }
        }
    }

}

fun main() {
    val solution = RomanToInteger.Solution()
    check(solution.romanToInt("III") == 3)
    check(solution.romanToInt("LVIII") == 58)
    check(solution.romanToInt("MCMXCIV") == 1994)
}