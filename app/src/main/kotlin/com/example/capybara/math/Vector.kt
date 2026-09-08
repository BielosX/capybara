package com.example.capybara.math

import android.os.Parcelable
import kotlin.math.sqrt
import kotlinx.parcelize.Parcelize

class IncorrectVectorSize(message: String) : RuntimeException(message)

@Parcelize
class Vector(private val dimensions: ArrayList<Float>) : Parcelable {

  init {
    if (dimensions.isEmpty()) {
      throw IncorrectVectorSize("Empty vector")
    }
  }

  constructor(vararg d: Float) : this(d.toCollection(ArrayList()))

  override fun hashCode(): Int {
    return dimensions.hashCode()
  }

  override fun equals(other: Any?): Boolean {
    return other is Vector && dimensions == other.dimensions
  }

  operator fun plus(other: Vector): Vector {
    if (other.size() != this.size()) {
      throw IncorrectVectorSize("this: ${this.size()}, other: ${other.size()}")
    }
    return Vector(
      dimensions.mapIndexed { index, f -> f + other.dimensions[index] }.toCollection(ArrayList())
    )
  }

  operator fun minus(other: Vector): Vector {
    if (other.size() != this.size()) {
      throw IncorrectVectorSize("this: ${this.size()}, other: ${other.size()}")
    }
    return Vector(
      dimensions.mapIndexed { index, f -> f - other.dimensions[index] }.toCollection(ArrayList())
    )
  }

  fun length(): Float {
    return sqrt(dimensions.fold(0.0f, { acc, f -> acc + f * f }))
  }

  fun size(): Int {
    return dimensions.size
  }

  fun project(d: Int): Vector {
    if (d > this.size()) {
      throw IncorrectVectorSize("Projection bigger than original vector")
    }
    return Vector(dimensions.take(d).toCollection(ArrayList()))
  }

  fun dot(other: Vector): Float {
    if (other.size() != this.size()) {
      throw IncorrectVectorSize("this: ${this.size()}, other: ${other.size()}")
    }
    return dimensions.zip(other.dimensions).map { it.first * it.second }.sum()
  }

  operator fun div(scalar: Float): Vector {
    return Vector(dimensions.map { it / scalar }.toCollection(ArrayList()))
  }

  fun normalized(): Vector {
    return this / length()
  }

  fun homogeneous(): Vector {
    val newDimensions = ArrayList(dimensions)
    newDimensions.add(1.0f)
    return Vector(newDimensions)
  }

  operator fun times(scalar: Float): Vector {
    return Vector(dimensions.map { it * scalar }.toCollection(ArrayList()))
  }

  operator fun set(index: Int, value: Float) {
    dimensions[index] = value
  }

  operator fun get(index: Int): Float = dimensions[index]
}
