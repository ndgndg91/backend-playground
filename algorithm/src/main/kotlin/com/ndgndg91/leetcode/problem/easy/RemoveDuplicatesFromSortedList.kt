package com.ndgndg91.leetcode.problem.easy

import java.util.TreeSet

class RemoveDuplicatesFromSortedList {
    /**
     * Example:
     * var li = ListNode(5)
     * var v = li.`val`
     * Definition for singly-linked list.
     * class ListNode(var `val`: Int) {
     *     var next: ListNode? = null
     * }
     */
    class ListNode(var `val`: Int) {
        var next: ListNode? = null

    }
    class Solution {
        fun deleteDuplicates(head: ListNode?): ListNode? {
            var current = head
            val set = TreeSet<Int>()

            while (current != null) {
                set.add(current.`val`)
                current = current.next
            }

            if (set.isEmpty()) return null

            val answer = ListNode(set.first())
            set.remove(set.first())
            current = answer

            while (set.isNotEmpty()) {
                val nextVal = set.first()
                set.remove(nextVal)
                current?.next = ListNode(nextVal)
                current = current?.next
            }

            return answer
        }
    }
}

fun main() {
    val solution = RemoveDuplicatesFromSortedList.Solution()
    val head =RemoveDuplicatesFromSortedList.ListNode(1)
    val second = RemoveDuplicatesFromSortedList.ListNode(1)
    val third = RemoveDuplicatesFromSortedList.ListNode(2)
    val fourth = RemoveDuplicatesFromSortedList.ListNode(3)
    val last = RemoveDuplicatesFromSortedList.ListNode(3)
    head.next = second
    second.next = third
    third.next = fourth
    fourth.next = last

    val answer = solution.deleteDuplicates(head)
    println(answer)

}