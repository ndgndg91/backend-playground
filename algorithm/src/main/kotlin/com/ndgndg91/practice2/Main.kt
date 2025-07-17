package com.ndgndg91.practice2

import kotlin.math.min


fun test() {
    val s = "abc"
    val t = "abcd"
    for (i in 0 until min(s.length, t.length)) {
        s[i]
    }
}