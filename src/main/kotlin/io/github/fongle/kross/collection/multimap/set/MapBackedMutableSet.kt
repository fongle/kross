package io.github.fongle.kross.collection.multimap.set

import io.github.fongle.kross.collection.multimap.MapBackedCollection

internal class MapBackedMutableSet<K, V>(map: MutableMap<K, MutableSet<V>>, key: K) : MapBackedCollection<K, MutableSet<V>, V>(map, key), MutableSet<V> {
    override fun newCollection() = mutableSetOf<V>()
}