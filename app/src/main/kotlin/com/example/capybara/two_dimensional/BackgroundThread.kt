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
import androidx.core.graphics.minus
import androidx.core.graphics.plus
import androidx.core.graphics.withSave
import kotlin.math.pow
import kotlin.math.sqrt

class BackgroundThread(private val surface: Surface, val x: Int, val y: Int) :
  Thread(), Handler.Callback {
  var handler: Handler? = null
  var choreographer: Choreographer? = null
  var touchPoint: Point? = null
  var analogPoint: Point? = null
  var radius: Int? = null

  init {
    val canvas = surface.lockCanvas(null)
    radius = canvas!!.height shr 2
    surface.unlockCanvasAndPost(canvas)
  }

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
    canvas.withSave {
      drawColor(Color.WHITE)
      drawRect(Rect(x, y, x + 100, y + 100), rectPaint)
      if (touchPoint != null) {
        val radius = this.height shr 2
        drawCircle(
          touchPoint!!.x.toFloat(),
          touchPoint!!.y.toFloat(),
          radius.toFloat(),
          outlinePaint,
        )
      }
      if (analogPoint != null) {
        drawCircle(analogPoint!!.x.toFloat(), analogPoint!!.y.toFloat(), 100f, analogPaint)
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
        analogPoint = null
      }
      is InputEvent.Moved -> {
        analogPoint = Point(event.x.toInt(), event.y.toInt())
        val vec = analogPoint?.minus(touchPoint!!)
        val x = vec!!.x.toDouble()
        val y = vec.y.toDouble()
        val len = sqrt(x.pow(2) + y.pow(2))
        if (len > radius!!) {
          vec.x = ((x / len) * radius!!).toInt()
          vec.y = ((y / len) * radius!!).toInt()
          analogPoint = touchPoint!!.plus(vec)
        }
      }

      is InputEvent.Pressed -> {
        touchPoint = Point(event.x.toInt(), event.y.toInt())
        analogPoint = Point(event.x.toInt(), event.y.toInt())
      }

      null -> {
        touchPoint = null
        analogPoint = null
      }
    }
    return true
  }
}
