package io.github.fongle.kross.collection.multimap.map

import io.github.fongle.kross.collection.multimap.Entry
import io.github.fongle.kross.collection.multimap.Multimap

internal class MultimapMapView<K, V>(private val multimap: Multimap<K, V>) : Map<K, Collection<V>> {
    override val size = multimap.keys.size
    override fun containsKey(key: K) = multimap.keys.contains(key)
    override fun containsValue(value: Collection<V>) = keys.any { multimap[it] == value }
    override fun get(key: K): Collection<V>? = if (key in multimap) multimap[key] else null
    override fun isEmpty() = multimap.isEmpty()

    override val entries: Set<Map.Entry<K, Collection<V>>> = object: AbstractSet<Map.Entry<K, Collection<V>>>() {
        override val size = multimap.keys.size
        override fun iterator() = object: Iterator<Map.Entry<K, Collection<V>>> {
            private val keys = multimap.keys.iterator()
            override fun hasNext() = keys.hasNext()
            override fun next(): Map.Entry<K, Collection<V>> = keys.next().let { Entry(it, multimap[it]) }
        }
    }

    override val keys = multimap.keys

    override val values = object: AbstractCollection<Collection<V>>() {
        override val size = keys.size
        override fun iterator() = object: Iterator<Collection<V>> {
            private val keys = multimap.keys.iterator()
            override fun hasNext() = keys.hasNext()
            override fun next() = multimap[keys.next()]
        }
    }
}