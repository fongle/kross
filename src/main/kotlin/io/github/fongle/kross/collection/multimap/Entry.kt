package io.github.fongle.kross.collection.multimap

internal data class Entry<out K, out V>(override val key: K, override val value: V) : Map.Entry<K, V>