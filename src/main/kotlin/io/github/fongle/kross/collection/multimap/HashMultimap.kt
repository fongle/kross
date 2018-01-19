package io.github.fongle.kross.collection.multimap

import java.util.*
import kotlin.collections.HashSet

class HashMultimap<K : Any, V : Any> : AbstractMapBasedSetMultimap<K, V>() {
    override fun newCollection() = HashSet<V>()
    override val map = HashMap<K, MutableSet<V>>()
}