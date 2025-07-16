package com.ndgndg91.inmemoryfilesystem

class Trie {
    var root = TrieNode()

    fun insert(word: String) {
        var current = root

        for (char in word) {
            current = current.children.getOrPut(char) { TrieNode() }
        }

        current.isEnd = true
    }

    /**
     *  Trie에 해당 단어가 정확히 존재하는지 확인합니다.
     */
    fun search(word: String): Boolean {
        TODO()
    }

    /**
     * 해당 접두사로 시작하는 단어가 하나라도 있는지 확인합니다.
     */
    fun startsWith(prefix: String): Boolean {
        TODO()
    }
}

class TrieNode() {
    val children = HashMap<Char, TrieNode>()
    var isEnd = false
}

fun main() {
    val trie = Trie()
    trie.insert("apple")
    println(trie)
}