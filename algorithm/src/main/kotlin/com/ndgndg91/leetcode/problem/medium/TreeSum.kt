package com.ndgndg91.leetcode.problem.medium

class TreeSum {
    class Solution {
        fun threeSum(nums: IntArray): List<List<Int>> {
            nums.sort()
            val zeroSums = mutableListOf<List<Int>>()

            for (i in 0 .. nums.lastIndex) {
                if (i > 0 && nums[i] == nums[i - 1]) continue
                var left = i + 1
                var right = nums.size - 1
                while (left < right) {
                    val sum = nums[i] + nums[left] + nums[right]
                    when {
                        sum == 0 -> {
                            zeroSums.add(listOf(nums[i], nums[left], nums[right]))

                            while (left < right && nums[left] == nums[left+1]) { left++ }
                            while (left < right && nums[right] == nums[right-1]) { right-- }
                            left++
                            right--
                        }
                        sum < 0 -> left++
                        sum > 0 -> right--
                    }
                }
            }

            return zeroSums
        }
    }
}

fun main() {
    val solution = TreeSum.Solution()
    val result = solution.threeSum(intArrayOf(-1,0,1,2,-1,-4))


    println("result: $result")

}