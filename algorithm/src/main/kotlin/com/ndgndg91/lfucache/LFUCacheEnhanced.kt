package com.ndgndg91.lfucache

class LFUCacheEnhanced(private val capacity: Int) {

    // Node는 값, 빈도수 외에 리스트 연결을 위한 prev/next 포인터를 가짐
    private class Node(
        val key: Int,
        var value: Int,
        var freq: Int = 1,
        var prev: Node? = null,
        var next: Node? = null
    )

    // 같은 빈도수를 가진 노드들을 LRU 순서로 관리하는 이중 연결 리스트
    private class DoublyLinkedList {
        private val head: Node = Node(-1, -1) // 더미 헤드
        private val tail: Node = Node(-1, -1) // 더미 테일
        val size: Int
            get() = _size
        private var _size: Int = 0

        init {
            head.next = tail
            tail.prev = head
        }

        fun addFirst(node: Node) {
            node.next = head.next
            node.prev = head
            head.next?.prev = node
            head.next = node
            _size++
        }

        fun remove(node: Node) {
            node.prev?.next = node.next
            node.next?.prev = node.prev
            _size--
        }

        fun removeLast(): Node? {
            if (isEmpty()) return null
            val lastNode = tail.prev!!
            remove(lastNode)
            return lastNode
        }

        fun isEmpty(): Boolean = _size == 0
    }

    private val nodeMap = HashMap<Int, Node>()
    private val freqMap = HashMap<Int, DoublyLinkedList>()
    private var minFreq = 0

    // 노드의 빈도수를 업데이트하고, 알맞은 빈도수 그룹으로 이동시키는 핵심 로직
    private fun updateNode(node: Node) {
        val oldList = freqMap[node.freq]!!
        oldList.remove(node)

        // 노드가 있던 리스트가 비었고, 그 빈도수가 minFreq였다면 minFreq를 1 증가
        if (node.freq == minFreq && oldList.isEmpty()) {
            minFreq++
        }

        node.freq++
        val newList = freqMap.getOrPut(node.freq) { DoublyLinkedList() }
        newList.addFirst(node)
    }

    fun get(key: Int): Int {
        val node = nodeMap[key] ?: return -1
        updateNode(node)
        return node.value
    }

    fun put(key: Int, value: Int) {
        if (capacity == 0) return

        if (nodeMap.containsKey(key)) {
            val node = nodeMap[key]!!
            node.value = value
            updateNode(node)
        } else {
            // 용량이 꽉 찼으면 LFU+LRU 노드 제거
            if (nodeMap.size >= capacity) {
                val lfuList = freqMap[minFreq]!!
                val nodeToRemove = lfuList.removeLast()!! // LFU 그룹의 LRU 아이템
                nodeMap.remove(nodeToRemove.key)
            }

            // 새 노드 생성 및 추가
            val newNode = Node(key, value)
            nodeMap[key] = newNode

            // 새 노드는 빈도수가 1이므로, 빈도수 1 그룹에 추가하고 minFreq를 1로 리셋
            val listForFreq1 = freqMap.getOrPut(1) { DoublyLinkedList() }
            listForFreq1.addFirst(newNode)
            minFreq = 1
        }
    }
}