package io.github.fongle.kross.collection.multimap

internal class MultimapIterator<out K, out V>(private val map: MutableMultimap<K, V>): MutableIterator<Map.Entry<K, V>> {
    private val keys = map.keys.iterator()
    private var entry: MutableIterator<Map.Entry<K, V>>? = null

    override fun hasNext(): Boolean {
        val current = entry
        if (current != null && current.hasNext()) return true
        return keys.hasNext()
    }

    override fun next(): Map.Entry<K, V> {
        val current = entry
        if (current != null && current.hasNext()) {
            return current.next()
        }
        val next = EntryIterator(keys.next())
        entry = next
        return next.next()
    }

    override fun remove() {
        val current = entry ?: throw IllegalStateException()
        current.remove()
    }

    private inner class EntryIterator(val key: K): MutableIterator<Map.Entry<K, V>> {
        private val values = map[key].iterator()
        override fun hasNext() = values.hasNext()
        override fun next() = Entry(key, values.next())
        override fun remove() = values.remove()
    }
}