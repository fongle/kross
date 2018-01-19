package io.github.fongle.kross.collection.multimap

interface MutableMultimap<K : Any, V : Any>: Multimap<K, V>, MutableIterable<Map.Entry<K, V>> {
    fun put(key: K, value: V)
    fun putAll(key: K, values: Collection<V>)
    fun remove(key: K, value: V): Boolean
    fun remove(key: K): Collection<V>
    fun clear()
    override val keys: MutableSet<K>
    override val values: MutableCollection<V>
    override fun get(key: K): MutableCollection<V>
    override val entries: MutableCollection<Map.Entry<K, V>>
}

interface MutableSetMultimap<K: Any, V: Any>: SetMultimap<K, V>, MutableMultimap<K ,V> {
    override fun get(key: K): MutableSet<V>
    override val entries: MutableSet<Map.Entry<K, V>>
    override fun remove(key: K): Set<V>
}