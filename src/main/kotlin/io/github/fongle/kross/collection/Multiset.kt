package io.github.fongle.kross.collection

interface Multiset<E>: Collection<E> {
    fun asMap(): Map<E, Int>
    fun count(element: E): Int
}