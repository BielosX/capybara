package com.example.capybara.math

class IncorrectMatrixSize(message: String) : RuntimeException(message)

class Matrix(private val rows: ArrayList<Vector>) {

  init {
    if (rows.isEmpty()) {
      throw IncorrectMatrixSize("Empty matrix")
    }
  }

  override fun hashCode(): Int {
    return rows.hashCode()
  }

  override fun equals(other: Any?): Boolean {
    return other is Matrix && rows == other.rows
  }

  operator fun times(vector: Vector): Vector {
    val rowSize = rows[0].size()
    if (rowSize != vector.size()) {
      throw IncorrectVectorSize("Matrix row size: $rowSize Vector size: ${vector.size()}")
    }
    return Vector(rows.map { it.dot(vector) }.toCollection(ArrayList()))
  }

  operator fun get(row: Int, column: Int): Float = rows[row][column]

  companion object {
    fun identity(size: Int): Matrix {
      if (size <= 0) {
        throw IncorrectMatrixSize("Matrix size <= 0")
      }
      val matrix = Matrix(ArrayList(List(size, { Vector(ArrayList(List(size, { 0.0f }))) })))
      matrix.rows.forEachIndexed { index, vector -> vector[index] = 1.0f }
      return matrix
    }

    fun normalizedInputMatrix(width: Int, height: Int): Matrix {
      val w = width.toFloat()
      val h = height.toFloat()
      val ratio = w / h
      /*
       x' = ratio * (x / w) = ratio/w * x
       y' = -(y / h) + 1
      */
      return Matrix(
        arrayListOf(
          Vector(ratio / w, 0.0f, 0.0f),
          Vector(0.0f, -1.0f / h, 1.0f),
          Vector(0.0f, 0.0f, 1.0f),
        )
      )
    }

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
          Vector(1.0f / ratio * w, 0.0f, 0.0f),
          Vector(0.0f, -h, h),
          Vector(0.0f, 0.0f, 1.0f),
        )
      )
    }
  }
}
