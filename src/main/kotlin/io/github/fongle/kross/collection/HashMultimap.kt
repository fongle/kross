package io.github.fongle.kross.collection

import java.util.*
import kotlin.NoSuchElementException

class HashMultimap<K, V> : Multimap<K, V> {
    private val items = HashMap<K, MutableSet<V>>()

    override fun put(key: K, value: V) {
        val existingValues = items[key]
        if (existingValues != null) {
            existingValues.add(value)
        } else {
            items.put(key, mutableSetOf(value))
        }
    }

    override fun putAll(key: K, values: Collection<V>) {
        val existingValues = items[key]
        if (existingValues != null) {
            existingValues.addAll(values)
        } else {
            items.put(key, values.toMutableSet())
        }
    }

    override fun remove(key: K, value: V): Boolean {
        val existingValues = items[key] ?: return false
        if (!existingValues.remove(value)) {
            return false
        }
        if (existingValues.isEmpty()) {
            items.remove(key)
        }
        return true
    }

    override fun remove(key: K): Collection<V> {
        return items.remove(key) ?: emptySet()
    }

    override fun get(key: K): Collection<V> {
        return items[key]?.let { Collections.unmodifiableSet(it) } ?: emptySet()
    }

    override fun contains(key: K): Boolean {
        return items[key]?.isNotEmpty() ?: false
    }

    override fun contains(key: K, value: V): Boolean {
        return items[key]?.contains(value) ?: false
    }

    override val size get() = items.values.map { it.size }.sum()

    override fun isEmpty() = items.isEmpty()

    override fun clear() = items.clear()

    override fun iterator(): Iterator<Map.Entry<K, V>> {
        return MultimapIterator(items.iterator())
    }

    override val entries: Set<Map.Entry<K, V>> = object : Set<Map.Entry<K, V>> {
        override val size get() = this@HashMultimap.size
        override fun contains(element: Map.Entry<K, V>) = contains(element.key, element.value)
        override fun containsAll(elements: Collection<Map.Entry<K, V>>) = elements.all { contains(it) }
        override fun isEmpty() = this@HashMultimap.isEmpty()
        override fun iterator() = this@HashMultimap.iterator()
    }

    override val values: Set<V> = object : Set<V> {
        override val size get() = this@HashMultimap.size
        override fun contains(element: V) = items.values.any { it.contains(element) }
        override fun containsAll(elements: Collection<V>) = elements.all { contains(it) }
        override fun isEmpty() = this@HashMultimap.isEmpty()
        override fun iterator() = items.asSequence().flatMap { it.value.asSequence() }.iterator()
    }

    override val keys: Set<K> = Collections.unmodifiableSet(items.keys)

    private class MultimapIterator<K, V>(private val entries: Iterator<Map.Entry<K, Collection<V>>>): Iterator<Map.Entry<K, V>> {
        private var values: Iterator<V> = Collections.emptyIterator()
        private var key: K? = null

        override fun hasNext(): Boolean {
            return values.hasNext() || entries.hasNext()
        }

        override fun next(): Map.Entry<K, V> {
            while (!values.hasNext()) {
                entries.next().let {
                    key = it.key
                    values = it.value.iterator()
                }
            }
            val currentKey = key?: throw NoSuchElementException()
            return Entry(currentKey, values.next())
        }

        private data class Entry<out K, out V>(override val key: K, override val value: V) : Map.Entry<K, V>
    }
}
