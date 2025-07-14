package com.ndgndg91.wordcounter

class WordCounter {
    private val wordMap = HashMap<String, Int>()
    fun mostCommonWord(paragraph: String, banned: Array<String>): String {
        val banned = banned.toSet()
        return paragraph
            .lowercase()
            .split(Regex("[^a-z]+")) // 영단어가 아닌 모든 기준으로 스플릿
            .filter { it.isNotBlank() && it !in banned }
            .groupingBy { it }
            .eachCount()
            .maxBy { it.value }.key
    }
}

// [^0-9] 숫자를 제외한 패턴