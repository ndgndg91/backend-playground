package com.ndgndg91.leetcode.problem

import java.math.BigInteger
import kotlin.plus


fun main() {
    val l1 = ListNode(2)
    l1.next = ListNode(4)
    l1.next!!.next = ListNode(3)
    val l2 = ListNode(5)
    l2.next = ListNode(6)
    l2.next!!.next = ListNode(4)
    Solution().addTwoNumbers(l1, l2)
}


class ListNode(var `val`: Int) {
    var next: ListNode? = null
}
class Solution {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val l1Sum = sum(l1)
        val l2Sum = sum(l2)

        val reversed = (l1Sum + l2Sum).toString().reversed()
        val node = ListNode(reversed.first().toString().toInt())
        var current = node
        var index = 1
        while (index <= reversed.lastIndex) {
            current.next = ListNode(reversed[index].toString().toInt())
            current = current.next!!
            index++
        }

        return node
    }

    private fun sum(l: ListNode?): BigInteger {
        var current: ListNode? = l
        var position = BigInteger.ONE
        var sum = BigInteger.ZERO
        while(current != null) {
            sum += BigInteger.valueOf(current.`val`.toLong()).multiply(position)
            current = current.next
            position = position * BigInteger.TEN
        }
        return sum
    }
}