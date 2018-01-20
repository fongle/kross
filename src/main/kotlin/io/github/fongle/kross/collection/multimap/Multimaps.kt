package io.github.fongle.kross.collection.multimap

import io.github.fongle.kross.collection.multimap.list.EmptyListMultimap
import io.github.fongle.kross.collection.multimap.list.ListMultimap
import io.github.fongle.kross.collection.multimap.list.ListMultimapImpl
import io.github.fongle.kross.collection.multimap.list.MutableListMultimap
import io.github.fongle.kross.collection.multimap.map.MultimapMapView
import io.github.fongle.kross.collection.multimap.set.EmptySetMultimap
import io.github.fongle.kross.collection.multimap.set.MutableSetMultimap
import io.github.fongle.kross.collection.multimap.set.SetMultimap
import io.github.fongle.kross.collection.multimap.set.SetMultimapImpl

fun <K, V> emptySetMultimap(): SetMultimap<K, V> = EmptySetMultimap<K, V>()
fun <K, V> emptyListMultimap(): ListMultimap<K, V> = EmptyListMultimap<K, V>()
fun <K, V> emptyMultimap(): Multimap<K, V> = emptyListMultimap()

fun <K, V> setMultimapOf(): SetMultimap<K, V> = emptySetMultimap()
fun <K, V> setMultimapOf(vararg pairs: Pair<K, V>): SetMultimap<K, V> = pairs.toMultimap(SetMultimapImpl()) { it }
fun <K, V> mutableSetMultimapOf(): MutableSetMultimap<K, V> = SetMultimapImpl()
fun <K, V> mutableSetMultimapOf(vararg pairs: Pair<K, V>): MutableSetMultimap<K, V> = pairs.toMultimap(SetMultimapImpl()) { it }

fun <K, V> listMultimapOf(): ListMultimap<K, V> = emptyListMultimap()
fun <K, V> listMultimapOf(vararg pairs: Pair<K, V>): ListMultimap<K, V> = pairs.toMultimap(ListMultimapImpl()) { it }
fun <K, V> mutableListMultimapOf(): MutableListMultimap<K, V> = ListMultimapImpl()
fun <K, V> mutableListMultimapOf(vararg pairs: Pair<K, V>): MutableListMultimap<K, V> = pairs.toMultimap(ListMultimapImpl()) { it }

fun <K, V> multimapOf(): Multimap<K, V> = emptyMultimap()
fun <K, V> multimapOf(vararg pairs: Pair<K, V>): Multimap<K, V> = listMultimapOf(*pairs)
fun <K, V> mutableMultimapOf(): MutableMultimap<K, V> = mutableListMultimapOf()
fun <K, V> mutableMultimapOf(vararg pairs: Pair<K, V>): MutableMultimap<K, V> = mutableListMultimapOf(*pairs)

fun <K, V> SetMultimap<K, V>.toMap() = keys.associate { it to this[it] }
fun <K, V> ListMultimap<K, V>.toMap() = keys.associate { it to this[it] }
fun <K, V> Multimap<K, V>.toMap() = keys.associate { it to this[it] }
fun <K, V> SetMultimap<K, V>.toMutableMap() = keys.associateTo(mutableMapOf()) { it to this[it] }
fun <K, V> ListMultimap<K, V>.toMutableMap() = keys.associateTo(mutableMapOf()) { it to this[it] }
fun <K, V> Multimap<K, V>.toMutableMap() = keys.associateTo(mutableMapOf()) { it to this[it] }

fun <K, V, T> Sequence<T>.toMultimap(block: (T) -> Pair<K, V>): Multimap<K, V> = toMultimap(mutableMultimapOf(), block)
fun <K, V, T, M: MutableMultimap<K, V>> Sequence<T>.toMultimap(multimap: M, block: (T) -> Pair<K, V>): M {
    forEach { value ->
        multimap.put(block(value))
    }
    return multimap
}

fun <K, V, T> Iterable<T>.toMultimap(block: (T) -> Pair<K, V>): Multimap<K, V> = asSequence().toMultimap(block)
fun <K, V, T, M: MutableMultimap<K, V>> Iterable<T>.toMultimap(multimap: M, block: (T) -> Pair<K, V>) = asSequence().toMultimap(multimap, block)
fun <K, V, T> Array<T>.toMultimap(block: (T) -> Pair<K, V>): Multimap<K, V> =  asSequence().toMultimap(block)
fun <K, V, T, M: MutableMultimap<K, V>> Array<T>.toMultimap(multimap: M, block: (T) -> Pair<K, V>) = asSequence().toMultimap(multimap, block)

fun <K, V> Iterable<Map.Entry<K, V>>.toMultimap() = toMultimap { it.toPair() }
fun <K, V> Array<Map.Entry<K, V>>.toMultimap() = toMultimap { it.toPair() }
fun <K, V> Sequence<Map.Entry<K, V>>.toMultimap() = toMultimap { it.toPair() }
fun <K, V> Iterable<Map.Entry<K, V>>.toMutableMultimap() = toMultimap(mutableMultimapOf()) { it.toPair() }
fun <K, V> Array<Map.Entry<K, V>>.toMutableMultimap() = toMultimap(mutableMultimapOf()) { it.toPair() }
fun <K, V> Sequence<Map.Entry<K, V>>.toMutableMultimap() = toMultimap(mutableMultimapOf()) { it.toPair() }

fun <K, V> MutableMultimap<K, V>.put(pair: Pair<K, V>) = put(pair.first, pair.second)

operator fun <K, V> MutableMultimap<K, V>.plusAssign(pair: Pair<K, V>) = Unit.also { put(pair) }
operator fun <K, V> Multimap<K, V>.plus(pair: Pair<K, V>) = toMutableMultimap().apply { put(pair) }

fun <K, V> Iterable<V>.indexBy(block: (V) -> K): Multimap<K, V> = toMultimap { block(it) to it }
fun <K, V> Array<V>.indexBy(block: (V) -> K): Multimap<K, V> = toMultimap { block(it) to it }
fun <K, V> Sequence<V>.indexBy(block: (V) -> K): Multimap<K, V> = toMultimap { block(it) to it }

/**
 * Represents a multimap as a [Map] from keys to collections of values. Changes
 * to the multimap are reflected in the returned collection.
 *
 * Note that where `multimap[key]` would return an empty collection,
 * `multimap.asMap[key]` returns `null`.
 */
val <K, V> Multimap<K, V>.asMap: Map<K, Collection<V>> get() = MultimapMapView(this)