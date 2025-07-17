package com.ndgndg91

import com.ndgndg91.practice.lfucache.LFUCache
import com.ndgndg91.practice.loggerratelimiter.Logger
import com.ndgndg91.practice.nextpermutation.NextPermutation
import com.ndgndg91.practice.twosum.TwoSum
import com.ndgndg91.practice.wordcounter.WordCounter


fun main() {
    TwoSum()
    NextPermutation()
    LFUCache(10).test()
    Logger().test()
    WordCounter().test()
}