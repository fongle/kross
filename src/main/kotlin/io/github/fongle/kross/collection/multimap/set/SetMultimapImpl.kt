package io.github.fongle.kross.collection.multimap.set

import io.github.fongle.kross.collection.multimap.AbstractMapBasedMultimap

internal class SetMultimapImpl<K, V> : AbstractMapBasedMultimap<K, V, MutableSet<V>>(mutableMapOf()), MutableSetMultimap<K, V> {
    override val entries: MutableSet<Map.Entry<K, V>> = MultimapEntrySet(this)
    override fun get(map: MutableMap<K, MutableSet<V>>, key: K): MutableSet<V> = MapBackedMutableSet(map, key)
    override fun newCollection() = mutableSetOf<V>()
}