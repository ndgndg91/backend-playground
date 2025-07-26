package com.ndgndg91.leetcode.problem.medium

class FourSum {
    class Solution {
        fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
            val answer = hashSetOf<List<Int>>()
            nums.sort()
            for (i in 0 .. nums.lastIndex) {
                for (j in i + 1 .. nums.lastIndex) {
                    var left = j + 1
                    var right = nums.lastIndex

                    while (left < right) {
                        val sum: Long = nums[i].toLong() + nums[j].toLong() + nums[left].toLong() + nums[right].toLong()
                        when {
                            sum == target.toLong() -> {
                                answer.add(listOf(nums[i], nums[j], nums[left], nums[right]))

                                while (left < right && nums[left] == nums[left + 1]) left++
                                while (left < right && nums[right] == nums[right - 1]) right--
                                left++
                                right--
                            }
                            sum < target -> left++
                            else -> right--
                        }
                    }
                }
            }

            return answer.toList()
        }
    }
}

fun main() {
    val solution = FourSum.Solution()
    val result = solution.fourSum(intArrayOf(1,0,-1,0,-2,2), 0)
    check(result.first() == listOf(-2,-1,1,2))
    check(result[1] == listOf(-2,0,0,2))
    check(result.last() == listOf(-1,0,0,1))
}