package io.github.fongle.kross.collection.multimap

import java.util.*

internal abstract class MapBackedCollection<K, out C : MutableCollection<V>, V>(private val map: MutableMap<K, C>, private val key: K) : MutableCollection<V> {
    protected abstract fun newCollection(): C
    private fun newCollection(value: V): C = newCollection().apply { add(value) }
    private fun newCollection(values: Collection<V>): C = newCollection().apply { addAll(values) }

    override fun addAll(elements: Collection<V>): Boolean {
        val existingValues = map[key]
        if (existingValues != null) {
            return existingValues.addAll(elements)
        }
        map[key] = newCollection(elements)
        return true
    }

    override fun removeAll(elements: Collection<V>): Boolean {
        val existingValues = map[key] ?: return false
        val modified = existingValues.removeAll(elements)
        if (modified && existingValues.isEmpty()) {
            map.remove(key)
        }
        return modified
    }

    override fun retainAll(elements: Collection<V>): Boolean {
        val existingValues = map[key] ?: return false
        val modified = existingValues.retainAll(elements)
        if (modified && existingValues.isEmpty()) {
            map.remove(key)
        }
        return modified
    }

    override fun add(element: V): Boolean {
        val existingValues = map[key]
        if (existingValues != null) {
            return existingValues.add(element)
        } else {
            map.put(key, newCollection(element))
            return true
        }
    }

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

    private val empty by lazy { newCollection() }
    protected val collection: C get() = map[key] ?: empty

    override val size get() = collection.size
    override fun contains(element: V) = collection.contains(element)
    override fun containsAll(elements: Collection<V>) = collection.containsAll(elements)
    override fun isEmpty() = collection.isEmpty()

    override fun hashCode() = collection.hashCode()
    override fun equals(other: Any?) = collection == other
    override fun toString() = collection.toString()
}

