package com.ndgndg91.practice.twosum


class TwoSum {
    data class Input(
        val input: Array<Int>,
        val target: Int,
        val output: List<Array<Int>>,
        val pairSize: Int
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Input

            if (target != other.target) return false
            if (pairSize != other.pairSize) return false
            if (!input.contentEquals(other.input)) return false
            if (output != other.output) return false

            return true
        }

        override fun hashCode(): Int {
            var result = target
            result = 31 * result + pairSize
            result = 31 * result + input.contentHashCode()
            result = 31 * result + output.hashCode()
            return result
        }


    }
    init {
        val testCases = listOf(
            Input(input = arrayOf(2, 7, 11, 15), target = 9, output = listOf(arrayOf(0, 1)), 1),
            Input(input = arrayOf(3, 2, 4), target = 6, output = listOf(arrayOf(1, 2)), 1),
            Input(input = arrayOf(3, 3), target = 6, output = listOf(arrayOf(0, 1)), 1),
            Input(input = arrayOf(-1, -3, 5, 90), target = 4, output = listOf(arrayOf(0, 2)), 1),
            Input(input = arrayOf(10, 20, 30, 40), target = 70, output = listOf(arrayOf(2, 3)), 1),
            Input(input = arrayOf(5, -4, 8, 11), target = 1, output = listOf(arrayOf(0, 1)), 1),
            Input(input = arrayOf(0, 4, 3, 0), target = 0, output = listOf(arrayOf(0, 3)), 1),
            Input(input = arrayOf(1, 2, 3, 4, 5), target = 9, output = listOf(arrayOf(3, 4)), 1),
            Input(input = arrayOf(-10, 7, 19, 15), target = 9, output = listOf(arrayOf(0, 2)), 1),
            Input(input = arrayOf(100, 200, 350, 400), target = 550, output = listOf(arrayOf(1, 2)), 1)
        )

        val multiplePairTestCase = listOf(
            Input(input = arrayOf(1, 3, 4, 5, 7, 8, 9, 10, 11, 15), target = 15, output = listOf(arrayOf(2, 8), arrayOf(3, 7), arrayOf(4, 5)), 3),
        )

        testCases.forEachIndexed { index, case ->
            val result = firstTwoSumBruteForce(case.input, case.target)
            check(result.contentEquals(case.output.first())) {
                "Test ${index + 1} failed: expected ${case.output.first().contentToString()}, got ${result.contentToString()}"
            }

            val result2 = firstTwoSum(case.input, case.target)
            check(result2.contentEquals(case.output.first())) {
                "Test ${index + 1} failed: expected ${case.output.first().contentToString()}, got ${result2.contentToString()}"
            }
        }
        multiplePairTestCase.forEachIndexed { index, case ->
            val result3 = twoSumPairs(case.input, case.target)
            check(result3.size == case.pairSize) {
                "Test ${index + 1} failed: expected size 1, got ${result3.size}"
            }
        }
        println("two sum pass")
    }

    /**
     * 이중 포문 O(n 제곱)
     */
    fun firstTwoSumBruteForce(array: Array<Int>, target: Int): Array<Int> {
        for ((iIndex, iValue) in array.withIndex() ) {
            for (jIndex in iIndex + 1 until array.size) {
                if (iValue + array[jIndex] == target) {
                    return arrayOf(iIndex, jIndex)
                }
            }
        }
        throw IllegalStateException("없음")
    }

    /**
     * hash map 을 통한 o(n)
     */
    fun firstTwoSum(array: Array<Int>, target: Int): Array<Int> {
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

    fun twoSumPairs(array: Array<Int>, target: Int): List<Array<Int>> {
        val map = hashMapOf<Int, Int>()
        val pairs = mutableListOf<Array<Int>>()
        array.forEachIndexed { index, i ->
            val complement = target - i
            val complementIndex = map.get(complement)
            if (complementIndex != null) {
                pairs.add(arrayOf(complementIndex, index))
            }

            map.put(i, index)
        }

        return pairs
    }
}
