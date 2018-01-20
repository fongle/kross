package io.github.fongle.kross.collection.multimap

internal class MultimapValues<K, V>(private val multimap: MutableMultimap<K, V>): AbstractMutableCollection<V>() {
    override fun add(element: V) = throw UnsupportedOperationException()
    override val size get() = multimap.size
    override fun iterator() = object: MutableIterator<V> {
        private val entries = multimap.iterator()
        override fun remove() = entries.remove()
        override fun hasNext() = entries.hasNext()
        override fun next() = entries.next().value
    }
    override fun contains(element: V) = multimap.keys.any { element in multimap[it] }
}