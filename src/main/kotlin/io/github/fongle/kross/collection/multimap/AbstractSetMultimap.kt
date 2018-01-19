package io.github.fongle.kross.collection.multimap

abstract class AbstractMapBasedSetMultimap<K : Any, V : Any>: AbstractMapBasedMultimap<K, V, MutableSet<V>>(), MutableSetMultimap<K, V> {
    abstract override val map: MutableMap<K, MutableSet<V>>

    override val entries: MutableSet<Map.Entry<K, V>> = object: AbstractMultimapEntries<K, V>(), MutableSet<Map.Entry<K, V>> {
        override val map: MutableMultimap<K, V> get() = this@AbstractMapBasedSetMultimap
        override fun equals(other: Any?) = setEquals(other)
        override fun hashCode() = sumBy { it.hashCode() }
    }

    override fun get(key: K): MutableSet<V> = ValueSet(key)

    private inner class ValueSet(key: K): ValueCollection(key), MutableSet<V> {
        override fun equals(other: Any?) = setEquals(other)
        override fun hashCode() = sumBy { it.hashCode() }
    }

    private fun <T> Collection<T>.setEquals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is Set<*>) return false
        if (other.size != size) return false
        return containsAll(other)
    }
}