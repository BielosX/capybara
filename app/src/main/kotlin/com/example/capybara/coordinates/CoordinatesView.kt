package com.example.capybara.coordinates

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.example.capybara.math.Matrix
import com.example.capybara.math.Vector
import kotlin.collections.get
import kotlin.div
import kotlin.times
import kotlin.unaryMinus

class CoordinatesView(context: Context) : View(context) {
  private val paint =
    Paint().apply {
      isAntiAlias = false
      color = Color.YELLOW
      textSize = 24f
      strokeWidth = 4f
    }

  private val normalizedLinesPaint =
    Paint().apply {
      isAntiAlias = false
      color = Color.BLUE
      textSize = 24f
      strokeWidth = 4f
    }

  // https://en.wikipedia.org/wiki/Homogeneous_coordinates
  private val normalizedLines =
    arrayListOf(
        Vector(0.0f, 0.0f, 1.0f),
        Vector(0.1f, 0.0f, 1.0f),
        Vector(0.0f, 0.0f, 1.0f),
        Vector(0.0f, 0.1f, 1.0f),
      )
      .map { it + Vector(0.1f, 0.1f, 0.0f) }
  // Start at (100, 100)
  private val offset = 100.0f
  /*
   (0,0) -> (200,0) X
   (0,0) -> (0, 200) Y
  */
  private val lines =
    floatArrayOf(0.0f, 0.0f, 200.0f, 0.0f, 0.0f, 0.0f, 0.0f, 200.0f)
      .map { it + offset }
      .toFloatArray()

  private lateinit var viewLines: FloatArray

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
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
    val viewMatrix =
      Matrix(
        arrayListOf(
          arrayListOf(1.0f / ratio * w, 0.0f, 0.0f),
          arrayListOf(0.0f, -h, h),
        )
      )
    viewLines =
      normalizedLines.map { viewMatrix * it }.flatMap { arrayListOf(it[0], it[1]) }.toFloatArray()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawColor(Color.BLACK)
    canvas.drawLines(lines, paint)
    canvas.drawText("X", 230.0f + offset, 0.0f + offset, paint)
    canvas.drawText("Y", 0.0f + offset, 230.0f + offset, paint)
    canvas.drawLines(viewLines, normalizedLinesPaint)
  }
}
