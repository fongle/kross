package io.github.fongle.kross.collection.multimap.list

import io.github.fongle.kross.collection.multimap.MutableMultimap

internal class MultimapEntryCollection<K, V>(private val multimap: MutableMultimap<K, V>) : AbstractMutableCollection<Map.Entry<K, V>>() {
    override val size get() = multimap.size
    override fun add(element: Map.Entry<K, V>) = multimap.put(element.key, element.value)
    override fun remove(element: Map.Entry<K, V>) = multimap.remove(element.key, element.value)
    override fun iterator(): MutableIterator<Map.Entry<K, V>> = multimap.iterator()
    override fun contains(element: Map.Entry<K, V>) = multimap.contains(element.key, element.value)

    override fun hashCode() = toArray().hashCode()

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other is Collection<*>) {
            val left = iterator()
            val right = other.iterator()
            while (left.hasNext() && right.hasNext()) if (left.next() != right.next()) return false
            if (!left.hasNext() && !right.hasNext()) return true
        }
        return false
    }
}