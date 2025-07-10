package com.ndgndg91.basiccalculator

class BasicCalculator {

    /**
     * "1 + 1"
     */
    fun calculate(s: String): Int {
        var result = 0
        var sign = 1
        var i = 0
        while (i <= s.lastIndex) {
            when {
                s[i].isDigit() -> {
                    var number = ""
                    while (i <= s.lastIndex && s[i].isDigit()) {
                        number += s[i]
                        i++
                    }
                    println("$number is a digit")
                    result += +number.toInt() * sign
                }
                s[i] == '+' -> {
                    println("${s[i]} is not a number")
                    sign = 1
                }
                s[i] == '-' -> {
                    println("${s[i]} isn not a number")
                    sign = -1
                }
            }
            i++
        }

        return result
    }
}

fun main() {
    check(BasicCalculator().calculate("1 + 12") == 13)
    check(BasicCalculator().calculate("1 - 12") == -11)

    check(BasicCalculator().calculate("1 + 12 + 2") == 15)
    check(BasicCalculator().calculate("1 - 12 - 4") == -15)
}