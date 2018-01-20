package io.github.fongle.kross.collection.multimap.set

internal class EmptySetMultimap<K, V> : SetMultimap<K, V> {
    override fun iterator() = entries.iterator()
    override val size = 0
    override val entries = emptySet<Map.Entry<K, V>>()
    override val values = emptySet<V>()
    override val keys = emptySet<K>()
    override fun get(key: K) = emptySet<V>()
    override fun contains(key: K) = false
    override fun contains(key: K, value: V) = false
    override fun isEmpty() = true
}