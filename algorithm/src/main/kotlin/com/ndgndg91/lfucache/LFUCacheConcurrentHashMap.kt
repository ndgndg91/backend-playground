package com.ndgndg91.lfucache

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class LFUCache(private val capacity: Int) {
    private val cache = ConcurrentHashMap<Int, Value>()
    private var logicalTime: Long = 0

    class Value(
        val counter: AtomicInteger,
        var operationTime: Long,
        var number: Int
    )

    fun get(key: Int): Int {
        val value = cache[key] ?: return -1

        value.counter.incrementAndGet()
        value.operationTime = logicalTime++

        return value.number
    }

    fun put(key: Int, value: Int) {
        if (capacity == 0) {
            return
        }

        if (cache.containsKey(key)) {
            val existingValue = cache[key]!!
            existingValue.counter.incrementAndGet()
            existingValue.operationTime = logicalTime++
            existingValue.number = value
        } else {
            if (cache.size >= capacity) {
                val target = cache.entries
                    .minWithOrNull(compareBy<Map.Entry<Int, Value>> { it.value.counter.get() }
                        .thenBy { it.value.operationTime })

                target?.let { cache.remove(it.key) }
            }
            cache.put(key, Value(AtomicInteger(1), logicalTime++, value))
        }
    }
}

fun main() {
    testCase1()
    testCase2()
}

private fun testCase2() {
    val cache = LFUCache(2)
    cache.put(3,1)
    cache.put(2,1)
    cache.put(2,2)
    cache.put(4,4)
    cache.get(2)
}

private fun testCase1() {
    val cache = LFUCache(2)
    cache.put(1,1)
    cache.put(2,2)
    println("cache.get(1) : ${cache.get(1)}")
    cache.put(3,3)
    println("cache.get(2) : ${cache.get(2)}")
    println("cache.get(3) : ${cache.get(3)}")
    cache.put(4,4)
    println("cache.get(1) : ${cache.get(1)}")
    println("cache.get(3) : ${cache.get(3)}")
    println("cache.get(4) : ${cache.get(4)}")
}
