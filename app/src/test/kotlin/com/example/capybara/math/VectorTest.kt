package com.example.capybara.math

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class VectorTest {

  @Test
  fun shouldReturnTrueWhenVectorsAreEqual() {
    assertEquals(Vector(1.0f, 2.0f, 3.0f), Vector(1.0f, 2.0f, 3.0f))
  }

  @Test
  fun shouldReturnFalseWhenVectorsAreNotEqual() {
    assertNotEquals(Vector(2.0f, 1.0f), Vector(1.0f, 2.0f))
  }

  @Test
  fun shouldReturnVectorProjection() {
    val v = Vector(1.0f, 2.0f)
    assertEquals(v.project(1), Vector(1.0f))
  }

  @Test
  fun shouldFailWhenRequestedProjectionBiggerThanVectorSize() {
    val v = Vector(1.0f, 2.0f)
    assertThrows<IncorrectVectorSize> {
      v.project(10)
    }
  }

  @Test
  fun shouldSumTwoVectors() {
    assertEquals(Vector(1.0f, 2.0f) + Vector(2.0f, 1.0f), Vector(3.0f, 3.0f))
  }
}
