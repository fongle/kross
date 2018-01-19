package io.github.fongle.kross.collection.multimap

interface Multimap<K: Any, V: Any>: Iterable<Map.Entry<K, V>> {
    val size: Int
    val entries: Collection<Map.Entry<K, V>>
    val values: Collection<V>
    val keys: Set<K>
    fun get(key: K): Collection<V>
    fun contains(key: K): Boolean
    fun contains(key: K, value: V): Boolean
    fun isEmpty(): Boolean
}

interface SetMultimap<K: Any, V: Any>: Multimap<K, V> {
    override fun get(key: K): Set<V>
    override val entries: Set<Map.Entry<K, V>>
}