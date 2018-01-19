package io.github.fongle.kross.collection.multimap

abstract class AbstractMultimapEntries<K : Any, V : Any>: AbstractMutableCollection<Map.Entry<K, V>>() {
    abstract protected val map: MutableMultimap<K, V>
    override val size get() = map.size
    override fun contains(element: Map.Entry<K, V>) = map.contains(element.key, element.value)
    override fun add(element: Map.Entry<K, V>) = true.also { map.put(element.key, element.value) }
    override fun clear() = map.clear()
    override fun remove(element: Map.Entry<K, V>) = map.remove(element.key, element.value)
    override fun iterator() = map.iterator()
}