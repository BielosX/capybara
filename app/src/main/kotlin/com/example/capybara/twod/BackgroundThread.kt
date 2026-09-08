package com.example.capybara.twod

import android.graphics.Color
import android.os.Looper
import android.view.Choreographer
import android.view.Surface
import androidx.core.graphics.withSave
import com.example.capybara.math.Matrix
import com.example.capybara.math.Vector

class BackgroundThread(
  private val surface: Surface,
  initX: Float,
  initY: Float,
  val width: Int,
  val height: Int,
  val maxVelocity: Float,
) : Thread() {
  var choreographer: Choreographer? = null
  var lastFrameTime: Long? = null
  var circlePosition: Vector = Vector(initX, initY)
  var inputHandler: JoystickInputHandler? = null
  val joystickRangeRadius: Float = 0.30f
  val joystickRadius: Float = 0.1f
  val playerRadius: Float = 0.1f
  val aspectRatio: Float = width.toFloat() / height.toFloat()
  val viewMatrix = Matrix.normalizedViewMatrix(width, height)

  fun doFrame(timeNanos: Long) {
    lastFrameTime = lastFrameTime ?: timeNanos
    val timeDiff = (timeNanos - lastFrameTime!!).toFloat() / 1_000_000_000f
    lastFrameTime = timeNanos
    inputHandler?.moveVec?.apply {
      circlePosition += this.project(2) * maxVelocity * timeDiff
    }
    val movePoint: Vector? = inputHandler?.movePoint ?: inputHandler?.touchPoint
    val canvas = surface.lockCanvas(null)
    val position = viewMatrix * circlePosition.homogeneous()
    canvas.withSave {
      drawColor(Color.WHITE)
      drawCircle(
        position[0],
        position[1],
        playerRadius * width * 1.0f / aspectRatio,
        circlePaint,
      )
      inputHandler?.touchPoint?.apply {
        val position = viewMatrix * this
        drawCircle(
          position[0],
          position[1],
          joystickRangeRadius * width * 1.0f / aspectRatio,
          outlinePaint,
        )
      }
      movePoint?.apply {
        val position = viewMatrix * this
        drawCircle(
          position[0],
          position[1],
          joystickRadius * width * 1.0f / aspectRatio,
          analogPaint,
        )
      }
    }
    surface.unlockCanvasAndPost(canvas)
    choreographer?.postFrameCallback(::doFrame)
  }

  override fun run() {
    Looper.prepare()
    Looper.myLooper()?.let {
      inputHandler = JoystickInputHandler(it, width, height, joystickRangeRadius)
    }
    choreographer = Choreographer.getInstance()
    choreographer?.postFrameCallback(::doFrame)
    Looper.loop()
  }

  fun quit() {
    inputHandler?.looper?.quitSafely()
  }
}
