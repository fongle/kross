package io.github.fongle.kross.collection.multimap.set

import io.github.fongle.kross.collection.multimap.Multimap

/**
 * A specialization of [Multimap] where each key-value pair
 * may appear only once.
 *
 * Retrieving a key returns a set of distinct values.
 */
interface SetMultimap<K, out V> : Multimap<K, V> {
    override fun get(key: K): Set<V>
    override val entries: Set<Map.Entry<K, V>>
}

