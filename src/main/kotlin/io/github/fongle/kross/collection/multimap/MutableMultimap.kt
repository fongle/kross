package io.github.fongle.kross.collection.multimap

interface MutableMultimap<K, V>: Multimap<K, V>, MutableIterable<Map.Entry<K, V>> {
    /**
     * Adds a key-value pair to the multimap. In implementations which do not permit
     * duplicate pairs this operation may have no effect.
     *
     * @return `true` if the size of the multimap increased as a result of the operation.
     */
    fun put(key: K, value: V): Boolean

    /**
     * Adds a key-value pair for every value in [values].
     * Equivalent to `values.forEach { put(key, it) }`.
     *
     * @return `true` if the size of the multimap increased as a result of the operation.
     */
    fun putAll(key: K, values: Collection<V>): Boolean

    /**
     * Removes the first key-value pair from the multimap where the key is [key]
     * and the value is [value].
     *
     * @return `true` if the size of the multimap decreased as a result of the operation.
     */
    fun remove(key: K, value: V): Boolean

    /**
     * Removes every key-value pair from the multimap where the key is [key].
     *
     * @return The values that were previously associated with the key.
     */
    fun remove(key: K): Collection<V>

    /**
     * Removes all key-value pairs from the multimap.
     */
    fun clear()

    override val keys: MutableSet<K>
    override val values: MutableCollection<V>
    override fun get(key: K): MutableCollection<V>
    override val entries: MutableCollection<Map.Entry<K, V>>
}