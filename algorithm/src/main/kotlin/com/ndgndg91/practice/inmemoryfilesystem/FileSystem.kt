package com.ndgndg91.practice.inmemoryfilesystem

import java.util.*

class FileSystem {

    class TrieNode {
        val children = TreeMap<String, TrieNode>()
        val isFile = false
        val content = ""
    }

    private val root = TrieNode()

    private fun findNode(path: String): TrieNode? {
        if (path == "/") return root

        val components = path.split("/").drop(1)
        var current = root

        for (component in components) {
            current = current.children[component] ?: return null
        }

        return current
    }


    fun ls(path: String) {

    }

    fun mkdir(path: String) {

    }

    fun addContentToFile(path: String, content: String) {

    }

    fun readContentFromFile(path: String): String {
        return ""
    }
}