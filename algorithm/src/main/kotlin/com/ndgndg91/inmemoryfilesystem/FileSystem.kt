package com.ndgndg91.inmemoryfilesystem

import java.util.*

class FileSystem {

    class TrieNode {
        val children = TreeMap<String, TrieNode>()
        var isFile = false
        val content = StringBuilder()
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


    fun ls(path: String): List<String> {
        val current = findNode(path)
        if (current == null) return emptyList()

        return current.children.keys.map { "$path/$it" }
    }

    fun mkdir(path: String) {
        if (path == "/") return

        val components = path.split("/").drop(1)
        var current = root

        for (component in components) {
            current = current.children.getOrPut(component) { TrieNode() }
        }
    }

    fun addContentToFile(path: String, content: String) {
        val split = path.split("/")
        val directoryPath = split.dropLast(1).joinToString("/")
        val current = findNode(directoryPath)
        if (current == null) return
        val node = TrieNode()
        node.isFile = true
        node.content.append(content)
        current.children.put(split.last(), node)
    }

    fun readContentFromFile(path: String): String {
        val current = findNode(path)
        if (current == null) return ""
        return if (current.isFile) current.content.toString()
        else ""
    }
}

fun main() {
    val fs = FileSystem()
    fs.mkdir("/home/ndgndg91")
    println(fs.ls("/home/ndgndg91"))
    fs.mkdir("/home/ndgndg91/a")
    fs.mkdir("/home/ndgndg91/b")
    fs.mkdir("/home/ndgndg91/c")
    println(fs.ls("/home/ndgndg91"))

    println("/home/ndgndg91/input.txt".split("/").dropLast(1).joinToString("/"))
    fs.addContentToFile("/home/ndgndg91/input.txt", "hello file system")
    println(fs.readContentFromFile("/home/ndgndg91/input.txt"))
}