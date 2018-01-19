package io.github.fongle.kross.collection.multimap

abstract class AbstractMultimapIterator<K : Any, V : Any>: MutableIterator<Map.Entry<K, V>> {
    abstract val map: MutableMultimap<K, V>

    protected val keys by lazy { map.keys.iterator() }

    private var entry: MutableIterator<Map.Entry<K, V>>? = null

    final override fun hasNext(): Boolean {
        val current = entry
        if (current != null && current.hasNext()) return true
        return keys.hasNext()
    }

    final override fun next(): Map.Entry<K, V> {
        val current = entry
        if (current != null && current.hasNext()) {
            return current.next()
        }
        val next = EntryIterator(keys.next())
        entry = next
        return next.next()
    }

    final override fun remove() {
        val current = entry ?: throw IllegalStateException()
        current.remove()
    }

    private inner class EntryIterator(val key: K): MutableIterator<Map.Entry<K, V>> {
        private val values = map.get(key).iterator()
        override fun hasNext() = values.hasNext()
        override fun next() = Entry(key, values.next())
        override fun remove() = values.remove()
    }
}