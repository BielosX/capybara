package com.example.capybara.coordinates

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CoordinatesView(context: Context) : View(context) {
  private val paint =
    Paint().apply {
      isAntiAlias = false
      color = Color.YELLOW
      textSize = 24f
      strokeWidth = 4f
    }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    // Start at (100, 100)
    val offset = 100.0f
    /*
     (0,0) -> (200,0) X
     (0,0) -> (0, 200) Y
    */
    val lines =
      floatArrayOf(0.0f, 0.0f, 200.0f, 0.0f, 0.0f, 0.0f, 0.0f, 200.0f)
        .map { it + offset }
        .toFloatArray()
    canvas.drawColor(Color.BLACK)
    canvas.drawLines(lines, paint)
    canvas.drawText("X", 230.0f + offset, 0.0f + offset, paint)
    canvas.drawText("Y", 0.0f + offset, 230.0f + offset, paint)
  }
}
