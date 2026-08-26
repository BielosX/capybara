package com.example.capybara.two_dimensional

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Choreographer
import android.view.Surface
import androidx.core.graphics.withSave
import kotlin.math.min

class BackgroundThread(private val surface: Surface, val x: Int, val y: Int) :
  Thread(), Handler.Callback {
  var handler: Handler? = null
  var choreographer: Choreographer? = null
  var touchPoint: Point? = null
  var movePoint: Point? = null

  private val rectPaint =
    Paint().apply {
      isAntiAlias = true
      color = Color.RED
      style = Paint.Style.FILL
    }

  private val outlinePaint =
    Paint().apply {
      isAntiAlias = true
      color = Color.GRAY
      style = Paint.Style.STROKE
      strokeWidth = 4f
    }

  private val analogPaint =
    Paint().apply {
      isAntiAlias = true
      color = Color.GRAY
      style = Paint.Style.FILL
    }

  fun doFrame(timeNanos: Long) {
    val canvas = surface.lockCanvas(null)
    val analogRangeRadius = canvas.height shr 2
    canvas.withSave {
      drawColor(Color.WHITE)
      drawRect(Rect(x, y, x + 100, y + 100), rectPaint)
      if (touchPoint != null) {
        drawCircle(
          touchPoint!!.x.toFloat(),
          touchPoint!!.y.toFloat(),
          analogRangeRadius.toFloat(),
          outlinePaint,
        )
      }
      if (movePoint != null && touchPoint != null) {
        val touchPointVec = Vector(touchPoint!!.x, touchPoint!!.y)
        val movePointVec = Vector(movePoint!!.x, movePoint!!.y)
        var vec = movePointVec.minus(touchPointVec)
        val scalar = min(analogRangeRadius.toFloat(), vec.length())
        vec = vec.normalized().multiply(scalar).plus(touchPointVec)
        drawCircle(vec.x, vec.y, 100f, analogPaint)
      }
    }
    surface.unlockCanvasAndPost(canvas)
    choreographer?.postFrameCallback(::doFrame)
  }

  override fun run() {
    Looper.prepare()
    Looper.myLooper()?.let { handler = Handler(it, this) }
    choreographer = Choreographer.getInstance()
    choreographer?.postFrameCallback(::doFrame)
    Looper.loop()
  }

  fun quit() {
    handler?.looper?.quitSafely()
  }

  override fun handleMessage(msg: Message): Boolean {
    when (val event = msg.data.getParcelable("event", InputEvent::class.java)) {
      InputEvent.Released -> {
        touchPoint = null
        movePoint = null
      }
      is InputEvent.Moved -> {
        movePoint = Point(event.x.toInt(), event.y.toInt())
      }

      is InputEvent.Pressed -> {
        touchPoint = Point(event.x.toInt(), event.y.toInt())
        movePoint = touchPoint!!
      }

      null -> {
        touchPoint = null
        movePoint = null
      }
    }
    return true
  }
}
