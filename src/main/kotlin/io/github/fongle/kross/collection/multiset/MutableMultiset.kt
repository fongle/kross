package io.github.fongle.kross.collection.multiset

/**
 * A [Multiset] that allows adding and removing elements.
 */
interface MutableMultiset<E>: Multiset<E>, MutableCollection<E>