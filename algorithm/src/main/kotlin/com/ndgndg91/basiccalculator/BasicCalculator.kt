package com.ndgndg91.basiccalculator

import java.util.*

class BasicCalculator {

    fun calculate(s: String): Int {
        val tokens = tokenize(s)
        val postfix = postfix(tokens)
        return evaluate(postfix)
    }

    fun tokenize(s: String): List<String> {
        val s = s.replace(" ", "")
        val tokens = mutableListOf<String>()

        var i = 0
        while (i <= s.lastIndex) {
            when {
                s[i].isDigit() -> {
                    var number = ""
                    while (i <= s.lastIndex && s[i].isDigit()) {
                        number += s[i]
                        i++
                    }
                    tokens.add(number)
                    continue
                }
                s[i] == '+' -> tokens.add(s[i].toString())
                s[i] == '-' -> tokens.add(s[i].toString())
                s[i] == '*' -> tokens.add(s[i].toString())
                s[i] == '/' -> tokens.add(s[i].toString())
                s[i] == '(' -> tokens.add(s[i].toString())
                s[i] == ')' -> tokens.add(s[i].toString())
            }
            i++
        }

        return tokens
    }

    fun postfix(tokens: List<String>): List<String> {
        val results = mutableListOf<String>()
        val stack = Stack<String>()
        for (token in tokens) {
            when (token) {
                "+", "-", "/", "*" -> {
                    while (stack.isNotEmpty() && stack.peek() != "(" && precedence(stack.peek()) >= precedence(token)) {
                        results.add(stack.pop())
                    }

                    stack.push(token)
                }
                "(" -> stack.push(token)
                ")" -> {
                    while (stack.isNotEmpty() && stack.peek() != "(") {
                        results.add(stack.pop())
                    }

                    // ")" 제거
                    if (stack.isNotEmpty()) {
                        stack.pop()
                    }
                }
                else -> results.add(token)
            }
        }

        while (stack.isNotEmpty()) {
            results.add(stack.pop())
        }

        return results
    }

    private fun precedence(token: String): Int {
        return when (token) {
            "+", "-" -> 1 // 낮은 우선순위
            "*", "/" -> 2 // 높은 우선순위
            else -> 0     // 괄호('(') 또는 그 외의 경우는 가장 낮은 순위로 처리
        }
    }

    fun evaluate(postfix: List<String>): Int {
        val stack = Stack<Int>()
        for (token in postfix) {
            when {
                token.toIntOrNull() != null -> { stack.push(token.toInt()) }
                token == "+" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a + b)
                }
                token == "-" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a - b)
                }
                token == "*" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a * b)
                }
                token == "/" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a / b)
                }
            }

        }
        return stack.pop()
    }

}

fun main() {
    val basicCalculator = BasicCalculator()
    check(basicCalculator.tokenize("1 - 12+15-30+15") == listOf("1", "-", "12", "+", "15", "-", "30", "+", "15"))
    check(basicCalculator.tokenize("1 - (12+15)-30+15") == listOf("1", "-", "(", "12", "+", "15", ")", "-", "30", "+", "15"))

    check(basicCalculator.postfix(basicCalculator.tokenize("1 - (12+15)-30+15")) == listOf("1","12","15","+","-","30", "-","15","+"))
    check(basicCalculator.evaluate(basicCalculator.postfix(basicCalculator.tokenize("1 - (12+15)-30+15"))) == -41)
    check(basicCalculator.calculate("1 - (12+15)-30+15") == -41)
}

