package com.ndgndg91.leetcode.problem.easy

class SearchInsertPosition {
    class Solution {
        fun searchInsert(nums: IntArray, target: Int): Int {
            var left = 0
            var right = nums.lastIndex
            while (left <= right) {
                val mid = left + (right - left) / 2
                when {
                    nums[mid] == target -> return mid
                    nums[mid] < target -> left = mid + 1
                    else -> right = mid - 1
                }
            }

            return left
        }
    }
}

fun main() {
    val solution = SearchInsertPosition.Solution()
    check(solution.searchInsert(intArrayOf(1,3,5,6), 5) == 2)
    check(solution.searchInsert(intArrayOf(1,3,5,6), 2) == 1)
    check(solution.searchInsert(intArrayOf(1,3,5,6), 7) == 4)
}