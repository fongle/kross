package io.github.fongle.kross.collection

internal class MultisetIterator<E>(items: MutableMap<E, Int>) : MutableIterator<E> {
    private val entries: MutableIterator<MutableMap.MutableEntry<E, Int>> = items.iterator()
    private var element: E? = null
    private var entry: MutableMap.MutableEntry<E, Int>? = null
    private var count = 0
    private var canRemove = false

    override fun hasNext(): Boolean {
        return count != 0 || entries.hasNext()
    }

    override fun next(): E {
        if (count <= 0) {
            entries.next().let {
                element = it.key
                count = it.value
                entry = it
            }
        }
        count--
        canRemove = true
        return element ?: throw NoSuchElementException()
    }

    override fun remove() {
        val lastEntry = entry
        if (lastEntry == null || !canRemove) throw IllegalStateException("Must call next() before calling remove()")
        val oldValue = lastEntry.value
        val newValue = oldValue - 1
        if (newValue < 1) {
            entries.remove()
        } else {
            lastEntry.setValue(newValue)
        }
        canRemove = false
    }
}