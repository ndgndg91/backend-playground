package com.ndgndg91.twosum


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

fun main() {
    val testCases = listOf(
        Input(arrayOf(2, 7, 11, 15), 9, arrayOf(0, 1)),
        Input(arrayOf(3, 2, 4), 6, arrayOf(1, 2)),
        Input(arrayOf(3, 3), 6, arrayOf(0, 1)),
        Input(arrayOf(1, 5, 3, 7, 8), 12, arrayOf(2, 4)),
        Input(arrayOf(10, 25, 30, 45), 55, arrayOf(1, 2)),
        Input(arrayOf(-1, -2, -3, -4, -5), -8, arrayOf(2, 4)),
        Input(arrayOf(0, 4, 3, 0), 0, arrayOf(0, 3)),
        Input(arrayOf(5, 1, 8, 2, 9), 10, arrayOf(1, 4)),
        Input(arrayOf(12, 34, 56, 78), 90, arrayOf(0, 3)),
        Input(arrayOf(-10, 15, 20, -5), 10, arrayOf(1, 3))
    )

    testCases.forEachIndexed { index, case ->
        val result = twoSum(case.input, case.target)
        check(result.contentEquals(case.output)) {
            "Test ${index + 1} failed: expected ${case.output.contentToString()}, got ${result.contentToString()}"
        }
    }
}

fun twoSum(array: Array<Int>, target: Int): Array<Int> {
//    val map = hashMapOf<Int, Int>()
//    array.forEachIndexed { index, i ->
//
//    }
//    for ( in array) {
//        val complement = target - i
//        val complementIndex = map.get(complement)
//        if (complementIndex != null) {
//
//        } else {
//            map.put(i, )
//        }
//    }
    return arrayOf(0,4)
}