package com.ndgndg91.leetcode.problem.easy

class MergeTwoSortedList {
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
        fun mergeTwoLists2(list1: ListNode?, list2: ListNode?): ListNode? {
            // Create a dummy head node to simplify the logic of adding the first element
            val dummyHead = ListNode(0)
            var current: ListNode? = dummyHead

            var p1 = list1
            var p2 = list2

            while (p1 != null && p2 != null) {
                if (p1.`val` <= p2.`val`) {
                    current?.next = p1
                    p1 = p1.next
                } else {
                    current?.next = p2
                    p2 = p2.next
                }
                current = current?.next
            }

            // If one list is exhausted, append the rest of the other list
            if (p1 != null) {
                current?.next = p1
            } else if (p2 != null) {
                current?.next = p2
            }

            return dummyHead.next // The ac
        }
        fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
            val list = mutableListOf<Int>()

            var node1 = list1
            while (node1 != null) {
                list.add(node1.`val`)
                node1 = node1.next
            }

            var node2 = list2
            while (node2 != null) {
                list.add(node2.`val`)
                node2 = node2.next
            }

            if (list.isEmpty()) {
                return null
            }
            list.sort()
            val answer = ListNode(list.first())
            var current = answer
            for (i in 1 .. list.lastIndex) {
                val next = ListNode(list[i])
                current.next = next
                current = next
            }
            return answer
        }
    }
}

fun main() {
    val solution = MergeTwoSortedList.Solution()
    val listNode1 = MergeTwoSortedList.ListNode(1)
    val listNode2 = MergeTwoSortedList.ListNode(2)
    val listNode3 = MergeTwoSortedList.ListNode(4)
    listNode1.next = listNode2
    listNode2.next = listNode3

    val listNode4 = MergeTwoSortedList.ListNode(1)
    val listNode5 = MergeTwoSortedList.ListNode(3)
    val listNode6 = MergeTwoSortedList.ListNode(4)
    listNode4.next = listNode5
    listNode5.next = listNode6

    val answer1 = MergeTwoSortedList.ListNode(1)
    val answer2 = MergeTwoSortedList.ListNode(1)
    val answer3 = MergeTwoSortedList.ListNode(2)
    val answer4 = MergeTwoSortedList.ListNode(3)
    val answer5 = MergeTwoSortedList.ListNode(4)
    val answer6 = MergeTwoSortedList.ListNode(4)
    answer1.next = answer2
    answer2.next = answer3
    answer3.next = answer4
    answer4.next = answer5
    answer5.next = answer6

    solution.mergeTwoLists(listNode1, listNode4)
}