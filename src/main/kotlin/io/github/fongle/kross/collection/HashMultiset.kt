package io.github.fongle.kross.collection

import java.util.Collections

class HashMultiset<E> constructor() : MutableMultiset<E> {
    private val items = HashMap<E, Int>()

    override fun clear() = items.clear()

    override fun retainAll(elements: Collection<E>): Boolean {
        val elementsToRetain = elements as? Multiset<E> ?: HashMultiset(elements)
        val iterator = items.iterator()
        var modified = false
        while (iterator.hasNext()) {
            val entry = iterator.next()
            val countToRetain = elementsToRetain.count(entry.key)
            if (countToRetain == 0) {
                iterator.remove()
                modified = true
            } else if (countToRetain < entry.value) {
                entry.setValue(countToRetain)
                modified = true
            }
        }
        return modified
    }

    override fun iterator(): MutableIterator<E> = MultisetIterator(items)

    constructor(collection: Collection<E>) : this() {
        if (collection is Multiset<E>) {
            items.putAll(collection.asMap())
        } else {
            collection.forEach {
                add(it)
            }
        }
    }

    override fun asMap(): Map<E, Int> = Collections.unmodifiableMap(items)

    override fun add(element: E): Boolean {
        items.put(element, count(element) + 1)
        return true
    }

    override fun count(element: E): Int {
        return items[element] ?: 0
    }

    override operator fun contains(element: E): Boolean {
        return count(element) > 0
    }

    override fun remove(element: E): Boolean {
        val count = count(element)
        if (count > 1) {
            items.put(element, count - 1)
            return true
        } else if (count == 1) {
            items.remove(element)
            return true
        } else {
            return false
        }
    }

    override fun isEmpty() = items.isEmpty()

    override val size get() = items.values.sum()

    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) return true
        val multiset = elements as? Multiset<E> ?: HashMultiset(elements)
        return multiset.asMap().all { (key, value) ->
            count(key) >= value
        }
    }

    override fun toString(): String {
        return items.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null) return false
        if (other is Multiset<*>) return other.asMap() == this.asMap()
        return false
    }

    override fun hashCode() = items.hashCode()
}