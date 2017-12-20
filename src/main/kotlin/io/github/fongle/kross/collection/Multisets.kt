package io.github.fongle.kross.collection

fun <T> mutableMultisetOf(vararg items: T): MutableMultiset<T> = HashMultiset(items.toList())

fun <T> multisetOf(vararg items: T): Multiset<T> = HashMultiset(items.toList())

fun <T> emptyMultiset(): Multiset<T> = object : Multiset<T> {
    override val size = 0
    override fun asMap() = emptyMap<T, Int>()
    override fun contains(element: T) = false
    override fun containsAll(elements: Collection<T>) = false
    override fun isEmpty() = true
    override fun iterator() = emptyList<T>().iterator()
    override fun count(element: T) = 0
}

fun <T> Collection<T>.toMultiset(): Multiset<T> = if (isNotEmpty()) {
    HashMultiset(this)
} else {
    emptyMultiset()
}

fun <T> Collection<T>.toMutableMultiset(): MutableMultiset<T> = HashMultiset(this)
