package com.example.capybara.math

import kotlin.math.sqrt

class IncorrectVectorLength(message: String) : RuntimeException(message)

class Vector(private val dimensions: ArrayList<Float>) {

  init {
    if (dimensions.isEmpty()) {
      throw IncorrectVectorLength("Empty vector")
    }
  }

  constructor(vararg d: Int) : this(d.map { it.toFloat() }.toCollection(ArrayList()))

  operator fun plus(other: Vector): Vector {
    if (other.size() != this.size()) {
      throw IncorrectVectorLength("this: ${this.size()}, other: ${other.size()}")
    }
    return Vector(
      dimensions.mapIndexed { index, f -> f + other.dimensions[index] }.toCollection(ArrayList())
    )
  }

  operator fun minus(other: Vector): Vector {
    if (other.size() != this.size()) {
      throw IncorrectVectorLength("this: ${this.size()}, other: ${other.size()}")
    }
    return Vector(
      dimensions.mapIndexed { index, f -> f - other.dimensions[index] }.toCollection(ArrayList())
    )
  }

  fun length(): Float {
    return sqrt(dimensions.reduce { acc, f -> acc + f * f })
  }

  fun size(): Int {
    return dimensions.size
  }

  fun normalized(): Vector {
    val len = length()
    return Vector(dimensions.map { it / len }.toCollection(ArrayList()))
  }

  operator fun times(scalar: Float): Vector {
    return Vector(dimensions.map { it * scalar }.toCollection(ArrayList()))
  }

  operator fun div(scalar: Float): Vector {
    return Vector(dimensions.map { it / scalar }.toCollection(ArrayList()))
  }

  operator fun get(index: Int): Float = dimensions[index]
}
