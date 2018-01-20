package io.github.fongle.kross.collection.multiset

/**
 * A [Set]-like collection where each element may appear more than once.
 *
 * Like a set, a multiset does not consider the order of elements when calculating equality.
 * However, unlike a set, a multiset does consider the number of times an element was added.
 * ```
 *
 * multisetOf(1, 2, 3) == multisetOf(3, 2, 1)
 *
 * multisetOf(1, 1) != multisetOf(1)
 * ```
 *
 * @param E the type of element contained in the multiset
 */
interface Multiset<out E>: Collection<E> {
    /** @return the number of times [element] appears in the multiset. */
    fun count(element: @UnsafeVariance E): Int

    /** @return the set of distinct elements appearing in the multiset. */
    val unique: Set<E>
}

/**
 * @return a new multiset containing all the elements from this multiset
 * as well as all the elements from [other], such that
 * `(a + b).count("x") == a.count("x") + b.count("x")`.
 */
operator fun <E> Multiset<E>.plus(other: Multiset<E>): MutableMultiset<E> {
    val result = toMutableMultiset()
    result.addAll(other)
    return result
}

/**
 * @return a new multiset containing only the elements that remain after removing
 * all of the elements in [other] from this multiset, such that
 * `(a - b).count("x") == a.count("x") - b.count("x")`.
 */
operator fun <E> Multiset<E>.minus(other: Multiset<E>): MutableMultiset<E> {
    val result = toMutableMultiset()
    result.removeAll(other)
    return result
}

/**
 * @return a new multiset containing only the elements that appear in both
 * multisets, such that
 * `(a intersect b).count("x") == min(a.count("x"), b.count("x"))`.
 */
infix fun <E> Multiset<E>.intersect(other: Multiset<E>): MutableMultiset<E> {
    val result = toMutableMultiset()
    result.retainAll(other)
    return result
}