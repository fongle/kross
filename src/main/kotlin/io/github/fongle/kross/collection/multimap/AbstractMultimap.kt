package io.github.fongle.kross.collection.multimap

internal abstract class AbstractMultimap<K, V, out C: MutableCollection<V>>: MutableMultimap<K, V> {
    protected abstract fun newCollection(): C
    private fun newCollection(values: Collection<V>): C = newCollection().apply { addAll(values) }

    override fun putAll(key: K, values: Collection<V>) = values.map { value -> put(key, value) }.any()
    override fun put(key: K, value: V) =  get(key).add(value)
    override fun remove(key: K, value: V) = get(key).remove(value)
    override fun iterator(): MutableIterator<Map.Entry<K, V>> = MultimapIterator(this)
    override fun contains(key: K) = get(key).isNotEmpty()
    override fun contains(key: K, value: V) = get(key).contains(value)

    override fun remove(key: K): C {
        val collection = get(key)
        val result = newCollection(collection)
        collection.clear()
        return result
    }

    override val values: MutableCollection<V> get() = MultimapValues(this)

    override fun hashCode() = entries.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other is Multimap<*, *>) return other.entries == entries
        return false
    }

    override fun toString() = entries.toString()
}