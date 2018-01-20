package io.github.fongle.kross.collection.multimap.list

import io.github.fongle.kross.collection.multimap.MutableMultimap

interface MutableListMultimap<K, V> : MutableMultimap<K, V>, ListMultimap<K, V> {
    override fun get(key: K): MutableList<V>
}