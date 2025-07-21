package com.ndgndg91.leetcode.problem.easy

import java.util.Stack

class ValidParentheses {
    class Solution {
        fun isValid(s: String): Boolean {
            val stack = Stack<Char>()
            for (i in 0 .. s.lastIndex) {
                when (s[i]) {
                    '(' -> stack.add(s[i])
                    '[' -> stack.add(s[i])
                    '{' -> stack.add(s[i])
                    ')' -> {
                        if (stack.isEmpty() || stack.peek() == '{' || stack.peek() == '[') return false
                        stack.pop()
                    }
                    ']' -> {
                        if (stack.isEmpty() || stack.peek() == '(' || stack.peek() == '{') return false
                        stack.pop()
                    }
                    '}' -> {
                        if (stack.isEmpty() || stack.peek() == '(' || stack.peek() == '[') return false
                        stack.pop()
                    }
                }
            }

            return stack.isEmpty()
        }
    }
}

fun main() {
    val solution = ValidParentheses.Solution()

    check(solution.isValid("()"))
    check(solution.isValid("()[]{}"))
    check(solution.isValid("([])"))
    check(!solution.isValid("(]"))
    check(!solution.isValid("([)]"))
    check(!solution.isValid("{{"))
    check(!solution.isValid("[["))
    check(!solution.isValid("(("))
}