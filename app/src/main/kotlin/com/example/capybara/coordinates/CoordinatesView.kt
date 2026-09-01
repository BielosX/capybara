package com.example.capybara.coordinates

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.example.capybara.math.Matrix
import com.example.capybara.math.Vector

class CoordinatesView(context: Context) : View(context) {
  private val paint =
    Paint().apply {
      isAntiAlias = false
      color = Color.YELLOW
      textSize = 36f
      strokeWidth = 4f
    }

  private val normalizedLinesPaint =
    Paint().apply {
      isAntiAlias = false
      color = Color.BLUE
      textSize = 36f
      strokeWidth = 4f
    }

  private val offsetVector = Vector(0.05f, 0.05f, 0.0f)
  // https://en.wikipedia.org/wiki/Homogeneous_coordinates
  private val normalizedLines =
    arrayListOf(
        Vector(0.0f, 0.0f, 1.0f),
        Vector(0.1f, 0.0f, 1.0f),
        Vector(0.0f, 0.0f, 1.0f),
        Vector(0.0f, 0.1f, 1.0f),
      )
      .map { it + offsetVector }

  private val normalizedX = Vector(0.11f, 0.0f, 1.0f) + offsetVector
  private val normalizedY = Vector(0.0f, 0.11f, 1.0f) + offsetVector

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
  private lateinit var viewX: Vector
  private lateinit var viewY: Vector

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    val viewMatrix = Matrix.normalizedViewMatrix(w, h)
    viewLines =
      normalizedLines.map { viewMatrix * it }.flatMap { arrayListOf(it[0], it[1]) }.toFloatArray()
    viewX = viewMatrix * normalizedX
    viewY = viewMatrix * normalizedY
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawColor(Color.BLACK)
    canvas.drawLines(lines, paint)
    canvas.drawText("X", 230.0f + offset, 0.0f + offset, paint)
    canvas.drawText("Y", 0.0f + offset, 230.0f + offset, paint)
    canvas.drawLines(viewLines, normalizedLinesPaint)
    canvas.drawText("X", viewX[0], viewX[1], normalizedLinesPaint)
    canvas.drawText("Y", viewY[0], viewY[1], normalizedLinesPaint)
  }
}
