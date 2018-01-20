package io.github.fongle.kross.collection.multimap.list

import io.github.fongle.kross.collection.multimap.Multimap

/**
 * A specialization of [Multimap] where duplicate key-value pairs
 * are allowed.
 *
 * Retrieving a key returns a list of values.
 */
interface ListMultimap<K, out V> : Multimap<K, V> {
    override fun get(key: K): List<V>
}

