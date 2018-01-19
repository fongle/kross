package io.github.fongle.kross.collection.multimap

abstract class AbstractMultimap<K : Any, V : Any, C: MutableCollection<V>>: MutableMultimap<K, V> {
    protected abstract fun newCollection(): C
    protected fun newCollection(value: V) = newCollection().apply { add(value) }
    protected fun newCollection(values: Collection<V>) = newCollection().apply { addAll(values) }

    override val entries: MutableCollection<Map.Entry<K, V>> = object: AbstractMultimapEntries<K, V>() {
        override val map: MutableMultimap<K, V> get() = this@AbstractMultimap
    }

    override fun putAll(key: K, values: Collection<V>) {
        values.forEach { put(key, it) }
    }

    override fun put(key: K, value: V) {
        get(key).add(value)
    }

    override fun remove(key: K, value: V) = get(key).remove(value)

    override fun iterator(): MutableIterator<Map.Entry<K, V>> = object: AbstractMultimapIterator<K, V>() {
        override val map get() = this@AbstractMultimap
    }

    override fun contains(key: K) = get(key).isNotEmpty()
    override fun contains(key: K, value: V) = get(key).contains(value)

    override fun remove(key: K): C {
        val collection = get(key)
        val result = newCollection(collection)
        collection.clear()
        return result
    }
}