package io.github.fongle.kross.anagram

class AnagramCalculator {
    fun findSingleWordAnagram(letters: CharArray): Set<String> {
        return emptySet()
    }

    fun findSingleWordAnagram(characters: String): Set<String> {
        val letters = characters.filter { it.isLetter() }.map { it.toLowerCase() }.toCharArray()
        return findSingleWordAnagram(letters)
    }
}