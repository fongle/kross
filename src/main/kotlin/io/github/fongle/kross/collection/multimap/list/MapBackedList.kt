package io.github.fongle.kross.collection.multimap.list

import io.github.fongle.kross.collection.multimap.MapBackedCollection

internal abstract class MapBackedList<K, V>(map: MutableMap<K, MutableList<V>>, key: K) : MapBackedCollection<K, MutableList<V>, V>(map, key), MutableList<V> {
    override fun get(index: Int) = collection[index]
    override fun indexOf(element: V) = collection.indexOf(element)
    override fun lastIndexOf(element: V) = collection.lastIndexOf(element)
    override fun add(index: Int, element: V) = collection.add(index, element)
    override fun addAll(index: Int, elements: Collection<V>) = collection.addAll(index, elements)
    override fun listIterator() = collection.listIterator()
    override fun listIterator(index: Int) = collection.listIterator(index)
    override fun removeAt(index: Int) = collection.removeAt(index)
    override fun set(index: Int, element: V) = collection.set(index, element)
    override fun subList(fromIndex: Int, toIndex: Int) = collection.subList(fromIndex, toIndex)
}