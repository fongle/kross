package io.github.fongle.kross.collection.multimap.list

import io.github.fongle.kross.collection.multimap.AbstractMapBasedMultimap

internal class ListMultimapImpl<K, V> : AbstractMapBasedMultimap<K, V, MutableList<V>>(mutableMapOf()), MutableListMultimap<K, V> {
    override val entries: MutableCollection<Map.Entry<K, V>> = MultimapEntryCollection(this)
    override fun get(map: MutableMap<K, MutableList<V>>, key: K): MutableList<V> = MapBackedMutableList(map, key)
    override fun newCollection() = mutableListOf<V>()
}