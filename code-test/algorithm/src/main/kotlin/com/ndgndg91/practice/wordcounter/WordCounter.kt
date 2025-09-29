package com.ndgndg91.practice.wordcounter

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

    fun test() {
        check(WordCounter().mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.", arrayOf("hit")) == "ball")
    }
}

// [^0-9] 숫자를 제외한 패턴