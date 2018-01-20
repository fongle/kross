package io.github.fongle.kross.collection.multimap.set

internal class MultimapEntrySet<K, V>(private val multimap: MutableSetMultimap<K, V>): AbstractMutableSet<Map.Entry<K, V>>() {
    override val size get() = multimap.size
    override fun add(element: Map.Entry<K, V>) = multimap.put(element.key, element.value)
    override fun remove(element: Map.Entry<K, V>) = multimap.remove(element.key, element.value)
    override fun iterator(): MutableIterator<Map.Entry<K, V>> = multimap.iterator()
    override fun contains(element: Map.Entry<K, V>) = multimap.contains(element.key, element.value)
}