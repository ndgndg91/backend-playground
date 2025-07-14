package com.ndgndg91

import com.ndgndg91.lfucache.LFUCache
import com.ndgndg91.loggerratelimiter.Logger
import com.ndgndg91.nextpermutation.NextPermutation
import com.ndgndg91.twosum.TwoSum


fun main() {
    TwoSum()
    NextPermutation()
    LFUCache(10).test()
    Logger().test()
}