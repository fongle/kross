package io.github.fongle.kross.collection.multiset

import java.util.*
import java.util.Collections.unmodifiableSet

internal class HashMultiset<E> : AbstractMultiset<E>() {
    private val items = HashMap<E, Int>()

    override val unique: Set<E> = unmodifiableSet(items.keys)
    override fun clear() = items.clear()

    override fun retainAll(elements: Collection<E>): Boolean {
        val elementsToRetain = elements as? Multiset<E> ?: elements.toMultiset()
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

    override fun add(element: E): Boolean {
        items[element] = count(element) + 1
        return true
    }

    override fun count(element: E): Int {
        return items[element] ?: 0
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

    override val size get() = items.values.sum()

    override fun containsAll(elements: Collection<E>): Boolean {
        if (elements.isEmpty()) return true
        val multiset = elements as? Multiset<E> ?: elements.toMultiset()
        return multiset.unique.all { key ->
            count(key) >= multiset.count(key)
        }
    }
}