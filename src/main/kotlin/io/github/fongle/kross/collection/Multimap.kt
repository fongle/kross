package io.github.fongle.kross.collection

interface Multimap<K, V>: Iterable<Map.Entry<K, V>> {
    val size: Int
    val entries: Set<Map.Entry<K, V>>
    val values: Set<V>
    val keys: Set<K>
    fun put(key: K, value: V)
    fun putAll(key: K, values: Collection<V>)
    fun remove(key: K, value: V): Boolean
    fun remove(key: K): Collection<V>
    fun get(key: K): Collection<V>
    fun contains(key: K): Boolean
    fun contains(key: K, value: V): Boolean
    fun isEmpty(): Boolean
    fun clear()
}