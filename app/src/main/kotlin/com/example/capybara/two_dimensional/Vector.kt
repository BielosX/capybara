package com.example.capybara.two_dimensional

import kotlin.math.sqrt

class Vector {
  val x: Float
  val y: Float

  constructor(x: Int, y: Int) {
    this.x = x.toFloat()
    this.y = y.toFloat()
  }

  constructor(x: Float, y: Float) {
    this.x = x
    this.y = y
  }

  fun minus(other: Vector): Vector {
    return Vector(this.x - other.x, this.y - other.y)
  }

  fun plus(other: Vector): Vector {
    return Vector(this.x + other.x, this.y + other.y)
  }

  fun length(): Float {
    return sqrt(x * x + y * y)
  }

  fun normalized(): Vector {
    val len = length()
    return Vector(x / len, y / len)
  }

  fun multiply(scalar: Float): Vector {
    return Vector(x * scalar, y * scalar)
  }
}
