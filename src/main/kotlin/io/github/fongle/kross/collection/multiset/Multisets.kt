package io.github.fongle.kross.collection.multiset

fun <T> mutableMultisetOf(vararg items: T): MutableMultiset<T> = items.asList().toMutableMultiset()
fun <T> multisetOf(vararg items: T): Multiset<T> = items.asList().toMultiset()

fun <T> emptyMultiset(): Multiset<T> = object : Multiset<T> {
    override val unique = emptySet<T>()
    override val size = 0
    override fun contains(element: T) = false
    override fun containsAll(elements: Collection<T>) = false
    override fun isEmpty() = true
    override fun iterator() = emptyList<T>().iterator()
    override fun count(element: T) = 0
}

fun <T> Collection<T>.toMultiset(): Multiset<T> = toMutableMultiset()

fun <T> Collection<T>.toMutableMultiset(): MutableMultiset<T> {
    val multiset = HashMultiset<T>()
    multiset.addAll(this)
    return multiset
}

fun <T> Multiset<T>.toMap(): Map<T, Int> = unique.associate { it to count(it) }