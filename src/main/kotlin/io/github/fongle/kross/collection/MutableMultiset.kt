package io.github.fongle.kross.collection

interface MutableMultiset<E>: Multiset<E>, MutableCollection<E> {
    override fun addAll(elements: Collection<E>) = elements.map { add(it) }.any { it }
    override fun removeAll(elements: Collection<E>) = elements.map { remove(it) }.any { it }
}