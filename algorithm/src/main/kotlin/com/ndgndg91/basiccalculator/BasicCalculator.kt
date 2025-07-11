package com.ndgndg91.basiccalculator

class BasicCalculator {

    fun calculate(s: String): Int {
        val s = s.replace(" ", "")
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
//                    println("$number is a digit")
                    result += +number.toInt() * sign
                    continue
                }
                s[i] == '+' -> {
//                    println("${s[i]} is not a number")
                    sign = 1
                }
                s[i] == '-' -> {
//                    println("${s[i]} isn not a number")
                    sign = -1
                }
            }
            i++
        }

        return result
    }
}

fun main() {
    val basicCalculator = BasicCalculator()
    check(basicCalculator.calculate(" ") == 0)
    check(basicCalculator.calculate("1 + 12") == 13)
    check(basicCalculator.calculate("1+12") == 13)
    check(basicCalculator.calculate("1 - 12") == -11)
    check(basicCalculator.calculate("1-12") == -11)

    check(basicCalculator.calculate("1 + 12 + 2") == 15)
    check(basicCalculator.calculate("1+12+2") == 15)
    check(basicCalculator.calculate("1 - 12 - 4") == -15)
    check(basicCalculator.calculate("1-12-4") == -15)

    check(basicCalculator.calculate("1 - 12+15-30+15") == -11)
}

