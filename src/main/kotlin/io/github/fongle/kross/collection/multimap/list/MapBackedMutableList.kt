package io.github.fongle.kross.collection.multimap.list

internal class MapBackedMutableList<K, V>(map: MutableMap<K, MutableList<V>>, key: K) : MapBackedList<K, V>(map, key), MutableList<V> {
    override fun newCollection() = mutableListOf<V>()
}