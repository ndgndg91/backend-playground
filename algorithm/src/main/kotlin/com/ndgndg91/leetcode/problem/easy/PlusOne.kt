package com.ndgndg91.leetcode.problem.easy

class PlusOne {
    class Solution {
        fun plusOne(digits: IntArray): IntArray {
            var entireOverflow = false
            for (i in digits.lastIndex downTo 0) {
                if (digits[i] + 1 >= 10) {
                    digits[i] = 0
                    if (i == 0) {
                        entireOverflow = true
                    }
                } else {
                    digits[i] = digits[i] + 1
                    break
                }
            }

            return if (entireOverflow) {
                intArrayOf(1, *digits)
            } else digits
        }
    }
}

fun main() {
    val solution = PlusOne.Solution()

    check(solution.plusOne(intArrayOf(1,2,3)).contentEquals(intArrayOf(1,2,4)))
    check(solution.plusOne(intArrayOf(4,3,2,1)).contentEquals(intArrayOf(4,3,2,2)))
    check(solution.plusOne(intArrayOf(9)).contentEquals(intArrayOf(1,0)))
    check(solution.plusOne(intArrayOf(9, 9)).contentEquals(intArrayOf(1,0,0)))
}