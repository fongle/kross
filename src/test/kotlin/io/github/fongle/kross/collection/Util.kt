package io.github.fongle.kross.collection

import org.junit.Assert

infix fun Any?.shouldBe(expected: Any?) = Assert.assertEquals(expected, this)
infix fun Unit.and(other: Any) = other
