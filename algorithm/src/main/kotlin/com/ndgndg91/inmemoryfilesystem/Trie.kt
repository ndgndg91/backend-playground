package com.ndgndg91.inmemoryfilesystem

class Trie {
    private var root = TrieNode()

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
        var current = root

        for (char in word) {
            current = current.children[char] ?: return false
        }

        return current.isEnd
    }

    /**
     * 해당 접두사로 시작하는 단어가 하나라도 있는지 확인합니다.
     */
    fun startsWith(prefix: String): Boolean {
        var current = root

        for (char in prefix) {
            current = current.children.get(char) ?: return false
        }

        return true
    }
}

class TrieNode() {
    val children = HashMap<Char, TrieNode>()
    var isEnd = false
}

fun main() {
    val trie = Trie()
    trie.insert("apple")
    check(trie.startsWith("app"))
    check(trie.startsWith("apple"))
    check(!trie.startsWith("apples"))

    check(!trie.search("app"))
    check(trie.search("apple"))
}