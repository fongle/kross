package io.github.fongle.kross.collection.multimap

import io.github.fongle.kross.collection.and
import io.github.fongle.kross.collection.shouldBe
import org.junit.Test

class HashMultimapTest {
    @Test
    fun `a new multimap should be empty`() = with(newMultimap()) {
        isEmpty() shouldBe true
    }

    @Test
    fun `an empty multimap should have size zero`() = with(newMultimap()) {
        size shouldBe 0
    }

    @Test
    fun `size should be zero after adding and removing a key`() = with(newMultimap()) {
        put("test", "value")
        remove("test", "value")
        size shouldBe 0
    }

    @Test
    fun `contains should be false for a key that has not yet been added`() = with(newMultimap()) {
        contains("test") shouldBe false
    }

    @Test
    fun `contains should be false for a key checked against the wrong value`() = with(newMultimap()) {
        put("test", "value")
        contains("test", "other-value") shouldBe false
    }

    @Test
    fun `contains should be true for a key-value pair that is in the map`() = with(newMultimap()) {
        put("test", "value")
        contains("test", "value") shouldBe true
    }

    @Test
    fun `size should be zero after clearing the multimap`() = with(newMultimap()) {
        put("test", "value")
        clear()
        size shouldBe 0
    }

    @Test
    fun `putAll should add multiple values for the same key`() = with(newMultimap()) {
        putAll("test", setOf("value-1", "value-2"))
        contains("test", "value-1") shouldBe true
        contains("test", "value-2") shouldBe true
    }

    @Test
    fun `contains should be true for a key that has at least one value`() = with(newMultimap()) {
        put("test", "value")
        contains("test") shouldBe true
    }

    @Test
    fun `adding a key should increase the size to 1`() = with(newMultimap()) {
        put("test", "value")
        size shouldBe 1
    }

    @Test
    fun `fetching a key that is not present should return an empty collection`() = with(newMultimap()) {
        get("test") shouldBe emptySet<String>()
    }

    @Test
    fun `fetching a key that has one value should return a singleton set`() = with(newMultimap()) {
        put("test", "value")
        get("test") shouldBe setOf("value")
    }

    @Test
    fun `fetching a key with multiple values should return a set of values`() = with(newMultimap()) {
        put("test", "value-2")
        put("test", "value")
        get("test") shouldBe setOf("value", "value-2")
    }

    @Test
    fun `remove should return the previous values for a key`() = with(newMultimap()) {
        put("test", "value-2")
        put("test", "value-1")
        remove("test") shouldBe setOf("value-1", "value-2")
    }

    @Test
    fun `remove should return an empty collection for a key that has no values`() = with(newMultimap()) {
        remove("test") shouldBe emptySet<String>()
    }

    @Test
    fun `remove should return true for a key-value pair that is in the map`() = with(newMultimap()) {
        put("test", "value")
        remove("test", "value") shouldBe true
    }

    @Test
    fun `remove should return false for a key-value pair that is not in the map`() = with(newMultimap()) {
        put("test", "value-1")
        remove("test", "value-2") shouldBe false
    }

    @Test
    fun `value set size should be zero when map is empty`() = with(newMultimap()) {
        values.size shouldBe 0
    }

    @Test
    fun `value set should be empty when map is empty`() = with(newMultimap()) {
        values.isEmpty() shouldBe true
    }

    @Test
    fun `key set size should be zero when map is empty`() = with(newMultimap()) {
        keys.size shouldBe 0
    }

    @Test
    fun `key set should be empty when map is empty`() = with(newMultimap()) {
        keys.isEmpty() shouldBe true
    }

    @Test
    fun `entry set size should be zero when map is empty`() = with(newMultimap()) {
        entries.size shouldBe 0
    }

    @Test
    fun `entry set should be empty when map is empty`() = with(newMultimap()) {
        entries.isEmpty() shouldBe true
    }

    @Test
    fun `contains should be false for a value that is not in the map`() = with(newMultimap()) {
        values.contains("test") shouldBe false
    }

    @Test
    fun `contains should be true for a value that appears anywhere in the map`() = with(newMultimap()) {
        put("key", "value")
        values.contains("value") shouldBe true
    }

    @Test
    fun `containsAll should be false if any values are missing`() = with(newMultimap()) {
        put("key", "value")
        values.containsAll(setOf("value", "key")) shouldBe false
    }

    @Test
    fun `key set and value set should have equality check`() = with(newMultimap()) {
        put("value", "key")
        put("key", "value")
        keys shouldBe values.toSet()
    }

    @Test
    fun `adding a key-value pair should be idempotent`() = with(newMultimap()) {
        put("key", "value")
        put("key", "value")
        size shouldBe 1 and get("key") shouldBe setOf("value")
    }

    @Test
    fun `adding a key-value pair with putAll should be idempotent`() = with(newMultimap()) {
        putAll("key", setOf("value"))
        put("key", "value")
        size shouldBe 1 and get("key") shouldBe setOf("value")
    }

    @Test
    fun `adding an entry via the entry set should be reflected in the map`() = with(newMultimap()) {
        entries.add(mutableMapOf("key" to "value").entries.single())
        contains("key", "value") shouldBe true
    }

    @Test
    fun `removing an entry via the entry set should be reflected in the map`() = with(newMultimap()) {
        put("key", "value")
        entries.remove(mapOf("key" to "value").entries.single())
        contains("key", "value") shouldBe false and isEmpty() shouldBe true
    }

    @Test
    fun `map should be empty after entry set is cleared`() = with(newMultimap()) {
        put("key", "value")
        entries.clear()
        isEmpty() shouldBe true
    }

    @Test
    fun `multiple entries should be removed from entry set using removeAll`() = with(newMultimap()) {
        put("key", "value-2")
        put("key" ,"value")
        entries.removeAll(entries.toList())
        isEmpty() shouldBe true
    }

    @Test
    fun `adding a value via a value collection should be reflected in the map`()  = with(newMultimap()) {
        get("key").add("value")
        get("key") shouldBe setOf("value")
    }

    @Test
    fun `removing a value via a value collection should be reflected in the map`()  = with(newMultimap()) {
        put("key", "value")
        get("key").remove("value")
        isEmpty() shouldBe true
    }

    private fun newMultimap() = mutableSetMultimapOf<String, String>()
}