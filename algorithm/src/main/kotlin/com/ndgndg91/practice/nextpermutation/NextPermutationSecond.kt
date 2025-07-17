package com.ndgndg91.practice.nextpermutation

class NextPermutationSecond {

    // 1,2,3,4
    // 1,2,4,3
    // 1,3,2,4
    // 1,3,4,2
    // 1,4,2,3
    // 1,4,3,2

    fun nextPermutation(array: Array<Int>): Array<Int> {
        val pivotIndex = findPivotIndex(array)
        val successorIndex = findSuccessorIndex(array, pivotIndex)
        swap(array, pivotIndex, successorIndex)
        reverse(array, pivotIndex+1)
        return array
    }

    private fun findPivotIndex(array: Array<Int>): Int {
        var pivotIndex = array.size - 2
        while (pivotIndex >= 0 ) {
            if (array[pivotIndex] < array[pivotIndex + 1]) {
                break
            }
            pivotIndex--
        }

        return pivotIndex
    }

    private fun findSuccessorIndex(array: Array<Int>, pivotIndex: Int): Int {
        var successorIndex = array.lastIndex
        while (pivotIndex < successorIndex) {
            if (array[successorIndex] > array[pivotIndex]) {
                break
            }
            successorIndex--
        }

        return successorIndex
    }

    private fun swap(array: Array<Int>, aIndex: Int, bIndex: Int) {
        val temp = array[aIndex]
        array[aIndex] = array[bIndex]
        array[bIndex] = temp
    }

    private fun reverse(array: Array<Int>, start: Int) {
        var i = start
        var j = array.lastIndex
        while (i < j) {
            swap(array, i, j)
            i++
            j--
        }
    }
}

fun main() {
    check(NextPermutationSecond().nextPermutation(arrayOf(1,2,3,4)).contentEquals(arrayOf(1,2,4,3)))
    check(NextPermutationSecond().nextPermutation(arrayOf(1,2,4,3)).contentEquals(arrayOf(1,3,2,4)))
    check(NextPermutationSecond().nextPermutation(arrayOf(1,3,2,4)).contentEquals(arrayOf(1,3,4,2)))
    check(NextPermutationSecond().nextPermutation(arrayOf(1,3,4,2)).contentEquals(arrayOf(1,4,2,3)))


    //
    // 1,4,3,2
}