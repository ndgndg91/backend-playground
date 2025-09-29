package com.ndgndg91.leetcode.problem.easy

class AddBinary {
    class Solution {
        fun optimize(a: String, b: String): String {
            var i = a.length - 1
            var j = b.length - 1
            var carry = 0
            val result = StringBuilder()

            while (i >= 0 || j >= 0 || carry == 1) {
                val sum = carry +
                        (if (i >= 0) a[i--] - '0' else 0) +
                        (if (j >= 0) b[j--] - '0' else 0)

                result.append(sum % 2) // The current bit is the sum modulo 2
                carry = sum / 2       // The carry for the next bit is the sum divided by 2
            }

            return result.reverse().toString()
        }

        fun addBinary(a: String, b: String): String {
            var aLastIndex = a.lastIndex
            var bLastIndex = b.lastIndex

            val builder = ArrayDeque<Char>()
            var carry = 0
            while (aLastIndex >= 0 && bLastIndex >= 0) {
                    when {
                        carry == 1 && a[aLastIndex] == '1' && b[bLastIndex] == '1' -> {
                            builder.addFirst('1')
                            carry = 1
                        }
                        carry == 1 && a[aLastIndex] == '0' && b[bLastIndex] == '0' -> {
                            builder.addFirst('1')
                            carry = 0
                        }
                        carry == 1 && a[aLastIndex] == '1' && b[bLastIndex] == '0' -> {
                            builder.addFirst('0')
                            carry = 1
                        }
                        carry == 1 && a[aLastIndex] == '0' && b[bLastIndex] == '1' -> {
                            builder.addFirst('0')
                            carry = 1
                        }
                        carry == 0 && a[aLastIndex] == '1' && b[bLastIndex] == '1' -> {
                            builder.addFirst('0')
                            carry = 1
                        }
                        carry == 0 && a[aLastIndex] == '0' && b[bLastIndex] == '0' -> {
                            builder.addFirst('0')
                            carry = 0
                        }
                        carry == 0 && a[aLastIndex] == '1' && b[bLastIndex] == '0' -> {
                            builder.addFirst('1')
                            carry = 0
                        }
                        carry == 0 && a[aLastIndex] == '0' && b[bLastIndex] == '1' -> {
                            builder.addFirst('1')
                            carry = 0
                        }
                    }

                aLastIndex--
                bLastIndex--
            }

            while (aLastIndex >= 0) {
                when {
                    carry == 1 && a[aLastIndex] == '1' -> {
                        builder.addFirst('0')
                        carry = 1
                    }
                    carry == 1 && a[aLastIndex] == '0' -> {
                        builder.addFirst('1')
                        carry = 0
                    }
                    carry == 0 && a[aLastIndex] == '1' -> {
                        builder.addFirst('1')
                        carry = 0
                    }
                    carry == 0 && a[aLastIndex] == '0' -> {
                        builder.addFirst('0')
                        carry = 0
                    }
                }

                aLastIndex--
            }

            while (bLastIndex >= 0) {
                when {
                    carry == 1 && b[bLastIndex] == '1' -> {
                        builder.addFirst('0')
                        carry = 1
                    }
                    carry == 1 && b[bLastIndex] == '0' -> {
                        builder.addFirst('1')
                        carry = 0
                    }
                    carry == 0 && b[bLastIndex] == '1' -> {
                        builder.addFirst('1')
                        carry = 0
                    }
                    carry == 0 && b[bLastIndex] == '0' -> {
                        builder.addFirst('0')
                        carry = 0
                    }
                }

                bLastIndex--
            }

            if (carry == 1) builder.addFirst('1')
            return builder.joinToString("")
        }
    }
}

fun main() {
    val solution = AddBinary.Solution()
    val addBinary = solution.addBinary("11", "1")
    check(addBinary == "100")
}