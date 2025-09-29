package com.ndgndg91.practice.nextpermutation

//3! = 3 * 2 * 1 = 6

//1,2,3
//1,3,2
//2,1,3
//2,3,1
//3,1,2
//3,2,1

//4! = 4 * 3 * 2 * 1 = 24
//[1, 2, 3, 4]
//[1, 2, 4, 3]
//[1, 3, 2, 4]
//[1, 3, 4, 2]
//[1, 4, 2, 3]
//[1, 4, 3, 2]
//[2, 1, 3, 4]
//[2, 1, 4, 3]
//[2, 3, 1, 4]
//[2, 3, 4, 1]
//[2, 4, 1, 3]
//[2, 4, 3, 1]
//[3, 1, 2, 4]
//[3, 1, 4, 2]
//[3, 2, 1, 4]
//[3, 2, 4, 1]
//[3, 4, 1, 2]
//[3, 4, 2, 1]
//[4, 1, 2, 3]
//[4, 1, 3, 2]
//[4, 2, 1, 3]
//[4, 2, 3, 1]
//[4, 3, 1, 2]
//[4, 3, 2, 1]

// 수가 많아 질수록 완전 탐색의 시간 복잡도가 기하급수적으로 늘어난다.
// 규칙을 파악하고, 적용한다.
class NextPermutation {
    init {
        check(nextPermutation(arrayOf(1,2,3)).contentEquals(arrayOf(1,3,2)))
        check(nextPermutation(arrayOf(1,3,2)).contentEquals(arrayOf(2,1,3)))
        check(nextPermutation(arrayOf(2,1,3)).contentEquals(arrayOf(2,3,1)))
        check(nextPermutation(arrayOf(2,3,1)).contentEquals(arrayOf(3,1,2)))
        check(nextPermutation(arrayOf(3,1,2)).contentEquals(arrayOf(3,2,1)))
        check(nextPermutation(arrayOf(1,2,3,4)).contentEquals(arrayOf(1,2,4,3)))
        check(nextPermutation(arrayOf(1,2,4,3)).contentEquals(arrayOf(1,3,2,4)))
        check(nextPermutation(arrayOf(1,3,2,4)).contentEquals(arrayOf(1,3,4,2)))
        check(nextPermutation(arrayOf(1,3,4,2)).contentEquals(arrayOf(1,4,2,3)))
        check(nextPermutation(arrayOf(1,4,2,3)).contentEquals(arrayOf(1,4,3,2)))
        check(nextPermutation(arrayOf(1,4,3,2)).contentEquals(arrayOf(2,1,3,4)))
        check(nextPermutation(arrayOf(2,4,3,1)).contentEquals(arrayOf(3,1,2,4)))

        check(nextPermutationOptimize(arrayOf(1,2,3)).contentEquals(arrayOf(1,3,2)))
        check(nextPermutationOptimize(arrayOf(1,2,3,4)).contentEquals(arrayOf(1,2,4,3)))
        check(nextPermutationOptimize(arrayOf(1,2,4,3)).contentEquals(arrayOf(1,3,2,4)))
        check(nextPermutationOptimize(arrayOf(1,3,2,4)).contentEquals(arrayOf(1,3,4,2)))
        check(nextPermutationOptimize(arrayOf(1,3,4,2)).contentEquals(arrayOf(1,4,2,3)))
        check(nextPermutationOptimize(arrayOf(1,4,2,3)).contentEquals(arrayOf(1,4,3,2)))
        check(nextPermutationOptimize(arrayOf(1,4,3,2)).contentEquals(arrayOf(2,1,3,4)))
        check(nextPermutationOptimize(arrayOf(2,4,3,1)).contentEquals(arrayOf(3,1,2,4)))
        println("next permutation pass")
    }
    // array 뒤에서 부터 읽다가 오름차순 아닌 값 pivot
    // pivot 보다 큰값 중 가장 작은 값과 swap
    // pivot 뒤에 값은 오름 차순 정렬
    fun nextPermutation(currentPermutation: Array<Int>): Array<Int> {
        for (i in currentPermutation.size -2 downTo   0) {
            if (currentPermutation[i] < currentPermutation[i+1]) {
                val pivot = currentPermutation[i]
                val pivotIndex = i
                // 배열의 맨 뒤부터 탐색
                var j = currentPermutation.size - 1
                // 피벗(pivot)보다 큰 값을 찾을 때까지 앞으로 이동
                while (currentPermutation[j] <= pivot) {
                    j--
                }
                val successorIndex = j // 찾은 인덱스를 바로 사용
                val temp = currentPermutation[pivotIndex]
                currentPermutation[pivotIndex] = currentPermutation[successorIndex]
                currentPermutation[successorIndex] = temp

                currentPermutation.reverse(pivotIndex+1, currentPermutation.size)
                break
            }
        }

        return currentPermutation
    }

    fun nextPermutationOptimize(arr: Array<Int>): Array<Int> {
        // 1. 피벗 찾기: a[i] < a[i+1] 을 만족하는 가장 큰 i
        var i = arr.size - 2
        while (i >= 0 && arr[i] >= arr[i+1]) {
            i--
        }

        if (i >= 0) {
            // 2. 피벗과 교환할 값(successor) 찾기: i 이후의 값 중 arr[i]보다 크면서 가장 작은 값
            var j = arr.size - 1
            while (arr[j] <= arr[i]) {
                j--
            }

            // 3. 피벗과 교환할 값(successor)을 교환
            swap(arr, i, j)
        }

        // 4. 피벗 위치(i) 다음부터 끝까지의 순서를 뒤집기
        // (만약 마지막 순열이었다면 i = -1이므로, 배열 전체가 뒤집혀 첫 순열이 됨)
        reverse(arr, i + 1)

        return arr
    }
    // 배열의 두 원소 위치를 바꾸는 헬퍼 함수
    private fun swap(arr: Array<Int>, i: Int, j: Int) {
        val temp = arr[i]
        arr[i] = arr[j]
        arr[j] = temp
    }
    // 배열의 특정 구간 순서를 뒤집는 헬퍼 함수
    private fun reverse(arr: Array<Int>, start: Int) {
        var i = start
        var j = arr.size - 1
        while (i < j) {
            swap(arr, i, j)
            i++
            j--
        }
    }
}