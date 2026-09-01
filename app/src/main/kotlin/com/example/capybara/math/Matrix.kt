package com.example.capybara.math

class IncorrectMatrixSize(message: String) : RuntimeException(message)

class Matrix(private val rows: ArrayList<ArrayList<Float>>) {

  init {
    if (rows.isEmpty()) {
      throw IncorrectMatrixSize("Empty matrix")
    }
  }

  operator fun times(vector: Vector): Vector {
    val rowSize = rows[0].size
    if (rowSize != vector.size()) {
      throw IncorrectVectorSize("Matrix row size: $rowSize Vector size: ${vector.size()}")
    }
    val results = ArrayList<Float>(ArrayList(List(rows.size) { 0f }))
    rows.forEachIndexed { row, rowItems ->
      rowItems.forEachIndexed { column, item -> results[row] += vector[column] * item }
    }
    return Vector(results)
  }

  companion object {
    fun normalizedViewMatrix(width: Int, height: Int): Matrix {
      val w = width.toFloat()
      val h = height.toFloat()
      val ratio = w / h
      /*
       ratio = width / height
       x' = 1/ratio * x * width = (1/ratio * width) * x + 0 * y + 0
       y' = height - height * y = 0 * x - height * y + height
       | (1/ratio * width) 0 0 |
       | 0 -height height |
      */
      return Matrix(
        arrayListOf(
          arrayListOf(1.0f / ratio * w, 0.0f, 0.0f),
          arrayListOf(0.0f, -h, h),
        )
      )
    }
  }
}
