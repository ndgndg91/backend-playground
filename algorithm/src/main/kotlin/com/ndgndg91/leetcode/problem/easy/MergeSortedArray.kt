package com.ndgndg91.leetcode.problem.easy

class MergeSortedArray {
    class Solution {
        fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {
            var left = 0
            var right = 0

            val merged = mutableListOf<Int>()

            while (left < m && right < n) {
                val leftValue = nums1[left]
                val rightValue = nums2[right]
                if (leftValue <= rightValue) {
                    merged.add(leftValue)
                    left++
                } else {
                    merged.add(rightValue)
                    right++
                }
            }
            while (left < m) {
                merged.add(nums1[left])
                left++
            }

            while (right < n) {
                merged.add(nums2[right])
                right++
            }

            merged.forEachIndexed { index, i ->
                nums1[index] = i
            }
        }
    }
}

fun main() {
    val solution = MergeSortedArray.Solution()
    val nums1 = intArrayOf(1, 2, 3, 0, 0, 0)
    solution.merge(nums1 = nums1, m = 3, nums2 = intArrayOf(2,5,6), n = 3)

    nums1.contentEquals(intArrayOf(1,2,2,3,5,6))
    println(nums1.contentToString())
}