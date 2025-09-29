package com.ndgndg91.leetcode.problem.easy

class ClimbingStairs {
    class Solution {
        fun climbStairs(n: Int): Int {
            if (n <= 2) return n

            val dp = mutableListOf<Int>()
            dp.add(0, 1)
            dp.add(1,2)

            for (i in 2..n - 1) {
                dp.add(i, dp[i - 1] + dp[i - 2])
            }

            return dp.last()
        }
    }
}

fun main() {
    val solution = ClimbingStairs.Solution()
    check(solution.climbStairs(4) == 5)
    check(solution.climbStairs(5) == 8)

}