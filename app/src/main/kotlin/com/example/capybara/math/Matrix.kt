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
}
