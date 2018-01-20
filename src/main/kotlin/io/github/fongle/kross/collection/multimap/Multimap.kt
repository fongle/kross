package io.github.fongle.kross.collection.multimap

/**
 * A [Map]-like structure where each key may be associated with more than one value.
 * Retrieving a key from the multimap returns a collection of zero or more values.
 * The iterator yields an individual key-value pair for each value associated with a key.
 *
 * @param K the type of keys in the multimap
 * @param V the type of values in the multimap
 */
interface Multimap<K, out V>: Iterable<Map.Entry<K, V>> {
    /**
     * The total number of key-value pairs in the multimap.
     */
    val size: Int

    /**
     * The key-value pairs in the multimap, as [Map.Entry] instances.
     *
     * Changes to the returned collection are reflected in the multimap, and vice versa.
     */
    val entries: Collection<Map.Entry<K, V>>

    /**
     * A [Collection] containing the value from every key-value pair in the multimap.
     *
     * Changes to the returned collection are reflected in the multimap, and vice versa.
     */
    val values: Collection<V>

    /**
     * A [Set] containing all the distinct keys in the multimap.
     *
     * Changes to the returned collection are reflected in the multimap, and vice versa.
     */
    val keys: Set<K>

    /**
     * @return A [Collection] containing all the values associated with [key]. When there
     * are no values, an empty collection is returned.
     *
     * Changes to the returned collection are reflected in the multimap, and vice versa.
     */
    operator fun get(key: K): Collection<V>

    /**
     * @return `true` if there are any values associated with [key].
     */
    operator fun contains(key: K): Boolean

    /**
     * @return `true` if the multimap contains at least one key-value pair where the
     * key is [key] and the value is [value].
     */
    fun contains(key: K, value: @UnsafeVariance V): Boolean

    /**
     * @return `true` if the multimap contains no key-value pairs.
     */
    fun isEmpty(): Boolean
}