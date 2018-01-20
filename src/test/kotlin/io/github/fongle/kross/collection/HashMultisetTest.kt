package io.github.fongle.kross.collection

import io.github.fongle.kross.collection.multiset.HashMultiset
import io.github.fongle.kross.collection.multiset.emptyMultiset
import io.github.fongle.kross.collection.multiset.multisetOf
import io.github.fongle.kross.collection.multiset.toMultiset
import org.junit.Test

class HashMultisetTest {
    @Test
    fun `it should return true when any item is added`() = with(newMultiset()) {
        add("test") shouldBe true
    }

    @Test
    fun `the count for an item which is not in the set should be zero`() = with(newMultiset()) {
        count("test") shouldBe 0
    }

    @Test
    fun `after adding an item once its count should be 1`() = with(newMultiset()) {
        add("test")
        count("test") shouldBe 1
    }

    @Test
    fun `it should return false when removing an item that is not in the set`() = with(newMultiset()) {
        remove("test") shouldBe false
    }

    @Test
    fun `count should still be zero after removing an item that is not in the set`() = with(newMultiset()) {
        remove("test")
        count("test") shouldBe 0
    }

    @Test
    fun `it should return true when removing an item that is in the set`() = with(newMultiset()) {
        add("test")
        remove("test") shouldBe true
    }

    @Test
    fun `after adding an item and then removing it, its count should be zero`() = with(newMultiset()) {
        add("test")
        remove("test")
        count("test") shouldBe 0
    }

    @Test
    fun `after adding an item twice and then removing it once, its count should be 1`() = with(newMultiset()) {
        add("test")
        add("test")
        remove("test")
        count("test") shouldBe 1
    }

    @Test
    fun `contains should be false for items not in the set`() = with(newMultiset()) {
        contains("test") shouldBe false
    }

    @Test
    fun `contains should be true for items in the set`() = with(newMultiset()) {
        add("test")
        contains("test") shouldBe true
    }

    @Test
    fun `after adding an item twice its count should be 2`() = with(newMultiset()) {
        add("test")
        add("test")
        count("test") shouldBe 2
    }

    @Test
    fun `isEmpty should be true for a new set`() = with(newMultiset()) {
        isEmpty() shouldBe true
    }

    @Test
    fun `isEmpty should be false after an item is added`() = with(newMultiset()) {
        add("test")
        isEmpty() shouldBe false
    }

    @Test
    fun `size should be zero for a new set`() = with(newMultiset()) {
        size shouldBe 0
    }

    @Test
    fun `size should reflect the number of items added`() = with(newMultiset()) {
        add("a")
        add("a")
        add("b")
        add("c")
        size shouldBe 4
    }

    @Test
    fun `size should reflect the number of items added and removed`() = with(newMultiset()) {
        add("a")
        add("a")
        add("b")
        add("c")
        remove("a")
        remove("b")
        remove("b")
        size shouldBe 2
    }

    @Test
    fun `containsAll should be false if the input contains more occurrences than the set`() = with(newMultiset()) {
        add("a")
        containsAll(listOf("a", "a")) shouldBe false
    }

    @Test
    fun `containsAll should be true if the set contains sufficient occurrences`() = with(newMultiset()) {
        add("a")
        add("a")
        add("b")
        containsAll(listOf("a", "a")) shouldBe true
    }

    @Test
    fun `iterator should contain no elements if the set is empty`() = with(newMultiset()) {
        iterator().hasNext() shouldBe false
    }

    @Test
    fun `iterator should iterate over all the elements that were added`() = with(newMultiset()) {
        add("a")
        add("a")
        toList() shouldBe listOf("a", "a")
    }

    @Test
    fun `containsAll should be true if the input is empty`()  = with(newMultiset()) {
        containsAll(emptyList()) shouldBe true
    }

    @Test
    fun `constructor should copy a multiset`() = with(newMultiset()) {
        add("test")
        this.toMultiset() shouldBe this
    }

    @Test
    fun `iterator should be able to remove items`() = with(newMultiset()) {
        add("a")
        add("a")
        add("b")
        with(iterator()) {
            forEachRemaining {
                remove()
            }
        }
        isEmpty() shouldBe true
    }

    @Test
    fun `addAll should add multiple items`() = with(newMultiset()) {
        addAll(listOf("a", "a", "b"))
        size shouldBe 3
    }

    @Test
    fun `addAll should do nothing if the input is empty`() = with(newMultiset()) {
        addAll(emptyList()) shouldBe false
    }

    @Test
    fun `removeAll should return true if the set was modified`() = with(newMultiset()) {
        add("test")
        removeAll(listOf("test")) shouldBe true
    }

    @Test
    fun `removeAll should return false if the set was not modified`() = with(newMultiset()) {
        add("a")
        removeAll(listOf("b")) shouldBe false
    }

    @Test
    fun `removeAll should reduce the number of occurrences`() = with(newMultiset()) {
        add("a")
        add("b")
        add("b")
        removeAll(listOf("a", "b"))
        count("b") shouldBe 1
    }

    @Test
    fun `set should be empty after calling clear`() = with(newMultiset()) {
        add("test")
        clear()
        isEmpty() shouldBe true
    }

    @Test
    fun `retainAll should empty the set if the input is empty`() = with(newMultiset()) {
        add("test")
        retainAll(emptySet())
        isEmpty() shouldBe true
    }

    @Test
    fun `retainAll should return true if the set is modified`() = with(newMultiset()) {
        add("test")
        retainAll(emptySet()) shouldBe true
    }

    @Test
    fun `retainAll should not modify the set if the input is contains everything in the set`() = with(newMultiset()) {
        add("a")
        add("b")
        add("a")
        retainAll(listOf("a", "a", "b"))
        size shouldBe 3
    }

    @Test
    fun `retainAll should return false if the set is not modified`() = with(newMultiset()) {
        add("a")
        retainAll(listOf("a")) shouldBe false
    }

    @Test
    fun `retainAll should reduce the number of occurrences`() = with(newMultiset()) {
        add("a")
        add("b")
        add("b")
        retainAll(listOf("b"))
        toList() shouldBe listOf("b")
    }

    @Test
    fun `should combine two multisets`() {
        multisetOf("a", "a") + multisetOf("a", "b") shouldBe multisetOf("a", "a", "a", "b")
    }

    @Test
    fun `adding an empty multiset should return an equal multiset`() {
        multisetOf("a") + emptyMultiset() shouldBe multisetOf("a")
    }

    @Test
    fun `should subtract one multiset from another`() {
        multisetOf("a", "a", "b") - multisetOf("a") shouldBe multisetOf("a", "b")
    }

    @Test
    fun `subtracting an empty multiset should return an equal multiset`() {
        multisetOf("a") - emptyMultiset() shouldBe multisetOf("a")
    }

    private fun newMultiset() = HashMultiset<String>()
}