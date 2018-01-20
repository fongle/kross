package io.github.fongle.kross.collection.multimap

internal abstract class AbstractMapBasedMultimap<K, V, C: MutableCollection<V>>(private val map: MutableMap<K, C>): AbstractMultimap<K, V, C>() {
    override val size get() = map.values.sumBy { it.size }
    override fun isEmpty() = map.isEmpty()
    override fun clear() = map.clear()
    override fun get(key: K): C = get(map, key)

    abstract fun get(map: MutableMap<K, C>, key: K): C

    override val keys get() = map.keys

    override fun hashCode() = map.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other is AbstractMapBasedMultimap<*, *, *>) return other.map == map
        if (other is Multimap<*, *>) return other.entries == entries
        return false
    }

    override fun toString() = map.toString()
}