package com.ndgndg91.practice.binarygap

class BinaryGap {
    fun solution(N: Int): Int {
        // Implement your solution here
        if (N > 0 && (N and (N - 1)) == 0) return 0
        val binaryString = Integer.toBinaryString(N)
        val gaps = mutableListOf<Int>()
        var i = 0
        while (i <= binaryString.lastIndex) {
            if (binaryString[i] == '1') {
                var gap = 0
                var j = i + 1
                while (j <= binaryString.lastIndex) {
                    if (binaryString[j] == '0') {
                        gap++
                    } else {
                        break
                    }

                    j++
                }
                gaps.add(gap)
            }
            i++
        }
        return gaps.maxBy { it }
    }

}

fun main() {
    val binaryGap = BinaryGap()
    check(binaryGap.solution(1) == 0)
    check(binaryGap.solution(2) == 0)
    check(binaryGap.solution(4) == 0)
    check(binaryGap.solution(8) == 0)
    check(binaryGap.solution(16) == 0)
    check(binaryGap.solution(32) == 0)
    check(binaryGap.solution(64) == 0)
    check(binaryGap.solution(128) == 0)
    check(binaryGap.solution(256) == 0)
    check(binaryGap.solution(512) == 0)
    check(binaryGap.solution(9) == 2)
    check(binaryGap.solution(529) == 4)
    check(binaryGap.solution(15) == 0)
    check(binaryGap.solution(1041) == 5)
}