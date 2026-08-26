package com.example.capybara.two_dimensional

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Choreographer
import android.view.Surface
import androidx.core.graphics.withSave
import kotlin.math.min

class BackgroundThread(private val surface: Surface, x: Int, y: Int, val maxVelocity: Float) :
  Thread(), Handler.Callback {
  var handler: Handler? = null
  var choreographer: Choreographer? = null
  var touchPoint: Point? = null
  var movePoint: Point? = null
  var lastFrameTime: Long? = null
  var circlePosition: Vector = Vector(x, y)

  private val circlePaint =
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
    lastFrameTime = lastFrameTime ?: timeNanos
    val timeDiff = (timeNanos - lastFrameTime!!).toFloat() / 1_000_000_000f
    lastFrameTime = timeNanos
    val canvas = surface.lockCanvas(null)
    val analogRangeRadius = canvas.height shr 2
    var deflectionVec: Vector? = null
    var touchPointVec: Vector? = null
    if (touchPoint != null && movePoint != null) {
      touchPointVec = Vector(touchPoint!!.x, touchPoint!!.y)
      val movePointVec = Vector(movePoint!!.x, movePoint!!.y)
      deflectionVec = movePointVec.minus(touchPointVec)
      val scalar = min(analogRangeRadius.toFloat(), deflectionVec.length())
      deflectionVec = deflectionVec.normalized().multiply(scalar)
    }
    if (deflectionVec != null && deflectionVec.length() > 0.1f) {
      val shift =
        deflectionVec.divide(analogRangeRadius.toFloat()).multiply(maxVelocity).multiply(timeDiff)
      circlePosition = circlePosition.plus(shift)
    }
    canvas.withSave {
      drawColor(Color.WHITE)
      drawCircle(circlePosition.x, circlePosition.y, 100.0f, circlePaint)
      if (touchPoint != null) {
        drawCircle(
          touchPoint!!.x.toFloat(),
          touchPoint!!.y.toFloat(),
          analogRangeRadius.toFloat(),
          outlinePaint,
        )
      }
      if (deflectionVec != null) {
        val vec = deflectionVec.plus(touchPointVec!!)
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
