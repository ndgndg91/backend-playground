package com.ndgndg91.twosum


class TwoSum {
    data class Input(
        val input: Array<Int>,
        val target: Int,
        val output: Array<Int>,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Input

            if (target != other.target) return false
            if (!input.contentEquals(other.input)) return false
            if (!output.contentEquals(other.output)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = target
            result = 31 * result + input.contentHashCode()
            result = 31 * result + output.contentHashCode()
            return result
        }
    }
    init {
        val testCases = listOf(
            Input(input = arrayOf(2, 7, 11, 15), target = 9, output = arrayOf(0, 1)),
            Input(input = arrayOf(3, 2, 4), target = 6, output = arrayOf(1, 2)),
            Input(input = arrayOf(3, 3), target = 6, output = arrayOf(0, 1)),
            Input(input = arrayOf(-1, -3, 5, 90), target = 4, output = arrayOf(0, 2)),
            Input(input = arrayOf(10, 20, 30, 40), target = 70, output = arrayOf(2, 3)),
            Input(input = arrayOf(5, -4, 8, 11), target = 1, output = arrayOf(0, 1)),
            Input(input = arrayOf(0, 4, 3, 0), target = 0, output = arrayOf(0, 3)),
            Input(input = arrayOf(1, 2, 3, 4, 5), target = 9, output = arrayOf(3, 4)),
            Input(input = arrayOf(-10, 7, 19, 15), target = 9, output = arrayOf(0, 2)),
            Input(input = arrayOf(100, 200, 350, 400), target = 550, output = arrayOf(1, 2))
        )

        testCases.forEachIndexed { index, case ->
            val result = twoSumBruteForce(case.input, case.target)
            check(result.contentEquals(case.output)) {
                "Test ${index + 1} failed: expected ${case.output.contentToString()}, got ${result.contentToString()}"
            }

            val result2 = twoSum(case.input, case.target)
            check(result2.contentEquals(case.output)) {
                "Test ${index + 1} failed: expected ${case.output.contentToString()}, got ${result2.contentToString()}"
            }
        }
        println("two sum pass")
    }
    fun twoSumBruteForce(array: Array<Int>, target: Int): Array<Int> {
        for ((iIndex, iValue) in array.withIndex() ) {
            for (jIndex in iIndex + 1 until array.size) {
                if (iValue + array[jIndex] == target) {
                    return arrayOf(iIndex, jIndex)
                }
            }
        }
        throw IllegalStateException("없음")
    }
    fun twoSum(array: Array<Int>, target: Int): Array<Int> {
        val map = hashMapOf<Int, Int>()
        array.forEachIndexed { index, i ->
            val complement = target - i
            val complementIndex = map.get(complement)
            if (complementIndex != null) {
                return arrayOf(complementIndex, index)
            } else {
                map.put(i, index)
            }
        }
        throw IllegalStateException("없음")
    }
}
