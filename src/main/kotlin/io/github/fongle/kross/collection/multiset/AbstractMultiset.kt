package io.github.fongle.kross.collection.multiset

abstract class AbstractMultiset<E>: MutableMultiset<E> {
    override fun addAll(elements: Collection<E>) = elements.map { add(it) }.any { it }
    override fun removeAll(elements: Collection<E>) = elements.map { remove(it) }.any { it }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other is Multiset<*>) {
            other as Multiset<E>
            return unique == other.unique && unique.all { e: E -> count(e) == other.count(e) }
        }
        return false
    }

    override fun hashCode() = sumBy { it?.hashCode() ?: 0 }
    override fun toString() = "[" + joinToString(", ") + "]"

    override fun isEmpty() = size == 0

    override operator fun contains(element: E): Boolean {
        return count(element) > 0
    }
}