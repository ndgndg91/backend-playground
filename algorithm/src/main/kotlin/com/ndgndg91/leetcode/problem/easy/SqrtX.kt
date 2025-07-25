package com.ndgndg91.leetcode.problem.easy

class SqrtX {
    class Solution {
        fun mySqrt(x: Int): Int {
            if (x >= 1 && x <= 3) return 1
            if (x >= 4 && x <= 8) return 2

            var left = 0
            var right = x
            var answer = 0

            while (left <= right) {
                val mid = left + (right - left) / 2

                if (mid.toLong() * mid.toLong() <= x) {
                    answer = mid
                    left = mid + 1
                } else {
                    right = mid - 1
                }
            }

            return answer
        }
    }
}

fun main() {
    val solution = SqrtX.Solution()
    check(solution.mySqrt(1) == 1)
    check(solution.mySqrt(2) == 1)
    check(solution.mySqrt(3) == 1)
    check(solution.mySqrt(4) == 2)
    check(solution.mySqrt(5) == 2)
    check(solution.mySqrt(6) == 2)
    check(solution.mySqrt(7) == 2)
    check(solution.mySqrt(8) == 2)
    check(solution.mySqrt(9) == 3)
}