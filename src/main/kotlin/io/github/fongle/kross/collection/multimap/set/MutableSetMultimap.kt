package io.github.fongle.kross.collection.multimap.set

import io.github.fongle.kross.collection.multimap.MutableMultimap

interface MutableSetMultimap<K, V> : SetMultimap<K, V>, MutableMultimap<K, V> {
    override fun get(key: K): MutableSet<V>
    override val entries: MutableSet<Map.Entry<K, V>>
    override fun remove(key: K): Set<V>
}