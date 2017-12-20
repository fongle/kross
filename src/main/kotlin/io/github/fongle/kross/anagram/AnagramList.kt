package io.github.fongle.kross.anagram

import java.text.Normalizer

class AnagramList(words: List<String>) {
    fun getAnagrams(word: String): Set<String> {
        return emptySet()
    }

    private fun normalize(s: String): String {
        return s.removeAccents().filter { it.isLetter() }.toLowerCase().toList().sorted().toString()
    }

    private fun String.removeAccents(): String {
        val decomposed = Normalizer.normalize(this, Normalizer.Form.NFD)
        return decomposed.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
    }
}