package com.example.capybara.math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MatrixTest {

  @Test
  fun shouldCreateIdentityMatrix() {
    val matrix = Matrix.identity(3)

    assertEquals(
      matrix,
      Matrix(
        arrayListOf(
          Vector(1.0f, 0.0f, 0.0f),
          Vector(0.0f, 1.0f, 0.0f),
          Vector(0.0f, 0.0f, 1.0f),
        )
      ),
    )
  }

  @ParameterizedTest
  @ValueSource(ints = [0, -10, -100])
  fun shouldFailToCreateIdentityMatrixWhenIncorrectSizeProvided(size: Int) {
    assertThrows<IncorrectMatrixSize> {
      Matrix.identity(size)
    }
  }
}
