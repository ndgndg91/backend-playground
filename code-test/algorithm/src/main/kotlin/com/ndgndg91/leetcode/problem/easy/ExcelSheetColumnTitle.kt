package com.ndgndg91.leetcode.problem.easy

class ExcelSheetColumnTitle {
    class Solution {
        /*
        동작 과정 예시 (28 -> "AB"):

        1단계: num = 28
        - num-- -> 27
        - 27 % 26 = 1 -> 'B'
        - result = "B"
        - num = 27 / 26 = 1

        2단계: num = 1
        - num-- -> 0
        - 0 % 26 = 0 -> 'A'
        - result = "A" + "B" = "AB"
        - num = 0 / 26 = 0

        결과: "AB"
        */
        fun convertToTitle(columnNumber: Int): String {
            var num = columnNumber
            var result = ""

            while (num > 0) {
                // 핵심: 1을 빼서 0-based로 만듦
                num--

                // 나머지로 문자 결정 (0->A, 1->B, ..., 25->Z)
                result = ('A' + (num % 26)) + result

                // 다음 자릿수로
                num /= 26
            }

            return result
        }
    }
}

fun main() {
    val solution = ExcelSheetColumnTitle.Solution()
    check(solution.convertToTitle(1) == "A")
    check(solution.convertToTitle(28) == "AB")
    check(solution.convertToTitle(701) == "ZY")
}