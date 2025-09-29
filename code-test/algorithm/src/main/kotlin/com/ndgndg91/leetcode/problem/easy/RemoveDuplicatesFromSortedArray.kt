package com.ndgndg91.leetcode.problem.easy

class RemoveDuplicatesFromSortedArray {

    class Solution {
        fun removeDuplicates(nums: IntArray): Int {
            if (nums.isEmpty()) return 0

            var i = 0
            for (j in 1 .. nums.lastIndex) {
                if (nums[i] != nums[j]) {
                    i++
                    nums[i] = nums[j]
                }
            }

            return i + 1
        }
    }
}

fun main() {
    val solution = RemoveDuplicatesFromSortedArray.Solution()
    check(solution.removeDuplicates(arrayOf(1,2,3,3).toIntArray()) == 3 )
}