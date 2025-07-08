fun main() {
    val array = arrayOf(1,2,3)

    val permutations = arrayOf(
        arrayOf(1,2,3),
        arrayOf(1,3,2),
        arrayOf(2,1,3),
        arrayOf(2,3,1),
        arrayOf(3,1,2),
        arrayOf(3,2,1),
    )

    val nextPermutation = nextPermutation(arrayOf(1,2,3))
    println(nextPermutation.contentToString())
}


// array 뒤에서 부터 읽다가 오름차순 아닌 값 pivot
// pivot 보다 큰값 중 가장 작은 값과 swap
// pivot 뒤에 값은 오름 차순 정렬
fun nextPermutation(currentPermutation: Array<Int>): Array<Int> {
    for (i in currentPermutation.size -2 downTo   0) {
        if (currentPermutation[i] < currentPermutation[i+1]) {
            val pivot = currentPermutation[i]
            val pivotIndex = i
            val successorIndex = (pivotIndex + 1 until currentPermutation.size)
                .filter { currentPermutation[it] > pivot }
                .minBy { currentPermutation[it] }
            val temp = currentPermutation[pivotIndex]
            currentPermutation[pivotIndex] = currentPermutation[successorIndex]
            currentPermutation[successorIndex] = temp

            currentPermutation.sort(successorIndex, currentPermutation.size - 1)
            break
        }
    }

    return currentPermutation
}

