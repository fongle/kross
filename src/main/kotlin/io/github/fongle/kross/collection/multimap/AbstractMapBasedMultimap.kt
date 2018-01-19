package io.github.fongle.kross.collection.multimap

import java.util.*

abstract class AbstractMapBasedMultimap<K : Any, V : Any, C: MutableCollection<V>>: AbstractMultimap<K, V, C>() {
    protected abstract val map: MutableMap<K, C>

    override val size get() = map.values.map { it.size }.sum()

    override fun isEmpty() = map.isEmpty()

    override fun clear() = map.clear()

    override fun get(key: K): MutableCollection<V> {
        return ValueCollection(key)
    }

    override val values: MutableCollection<V> = object : AbstractMutableCollection<V>() {
        override fun add(element: V) = throw UnsupportedOperationException()
        override val size get() = map.values.sumBy { it.size }
        override fun iterator() = object: MutableIterator<V> {
            val entries = this@AbstractMapBasedMultimap.iterator()
            override fun remove() = entries.remove()
            override fun hasNext() = entries.hasNext()
            override fun next() = entries.next().value
        }
        override fun contains(element: V) = map.values.any { it.contains(element) }
    }

    protected open inner class ValueCollection(val key: K) : AbstractMutableCollection<V>() {
        override val size get() = map[key]?.size ?: 0
        override fun add(element: V): Boolean {
            val existingValues = map[key]
            if (existingValues != null) {
                return existingValues.add(element)
            } else {
                map.put(key, newCollection(element))
                return true
            }
        }

        override fun contains(element: V) = map[key]?.contains(element) ?: false

        override fun isEmpty() = map[key]?.isEmpty() ?: true

        override fun clear() {
            map[key]?.clear()
            map.remove(key)
        }

        override fun remove(element: V): Boolean {
            val existingValues = map[key] ?: return false
            val removed = existingValues.remove(element)
            if (removed && existingValues.isEmpty()) {
                map.remove(key)
            }
            return removed
        }

        override fun iterator() = object : MutableIterator<V> {
            private val baseIterator: MutableIterator<V> = map[key]?.iterator() ?: Collections.emptyIterator()
            override fun hasNext() = baseIterator.hasNext()
            override fun next() = baseIterator.next()
            override fun remove() {
                baseIterator.remove()
                if (isEmpty()) {
                    map.remove(key)
                }
            }
        }
    }

    override val keys get() = map.keys
}