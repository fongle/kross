package io.github.fongle.kross.anagram

fun loadScowlWordList(): List<String> {
    val words = AnagramCalculator::class.java.getResourceAsStream("/scowl.txt").reader(Charsets.UTF_8).readLines()
    val transformed = words.filterNot { it.contains('\'') }
    return transformed
}

fun main(args: Array<String>) {
    val finder = AnagramList(loadScowlWordList())
    println(finder.getAnagrams("triangle"))
}