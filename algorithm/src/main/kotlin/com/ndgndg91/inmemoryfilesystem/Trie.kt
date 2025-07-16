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

    fun getWordsWithPrefix(prefix: String): List<String> {
        var current = root

        for (char in prefix) {
            current = current.children[char] ?: return emptyList()
        }

        val startNode = current
        val words = mutableListOf<String>()
        findEnd(startNode, StringBuilder(prefix), words)
        return words
    }

    private fun findEnd(node: TrieNode, builder: StringBuilder, words: MutableList<String>) {
        if (node.isEnd) {
            words.add(builder.toString())
        }

        for ((char, childNode) in node.children) {
            builder.append(char)
            findEnd(childNode, builder, words)
            builder.deleteCharAt(builder.length - 1)
        }
    }

    fun delete(word: String) {
        val nodeStack = ArrayDeque<TrieNode>()
        nodeStack.addLast(root)

        for (char in word) {
            val next = nodeStack.last().children[char] ?: return
            nodeStack.addLast(next)
        }

        val lastNode = nodeStack.last()
        if (!lastNode.isEnd) {
            return
        }

        lastNode.isEnd = false

        for (i in word.lastIndex downTo 0) {
            val current = nodeStack.removeLast()
            val parentNode = nodeStack.last()

            if (current.isEnd && current.children.isEmpty()) {
                parentNode.children.remove(word[i])
            } else {
                break
            }
        }

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

    trie.delete("apple")
    check(!trie.search("apple"))
    trie.insert("apple")
    check(trie.search("apple"))
    trie.delete("app")
    check(!trie.search("app"))
    check(trie.search("apple"))

    check(trie.getWordsWithPrefix("app") == listOf("apple"))
    trie.insert("application")
    check(trie.getWordsWithPrefix("app") == listOf("apple", "application"))
}