package com.ndgndg91.practice.triplet

class Triplet {
    fun get(t: Long, d: Array<Int>): Int {
        d.sort()
        var counter = 0
        var first: Long
        var second: Long
        var third: Long


        for (i in 0 until d.size) {
            first = d[i].toLong()

            var left = i + 1
            var right = d.lastIndex

            while (left < right) {
                second = d[left].toLong()
                third = d[right].toLong()

                println("$first $second $third ${first + second + third}")
                if (first + second + third <= t) {
                    counter++
                    right--
                } else {
                    left++
                }
            }

        }

        return counter
    }
}

fun main() {
    val triplet = Triplet()
    check(triplet.get(8L, arrayOf(1, 2, 3, 4, 5)) == 3)
    check(triplet.get(3L, arrayOf(1, 2, 3, 4, 5)) == 0)
}

