package com.example.capybara.twod

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.capybara.math.Matrix
import com.example.capybara.math.Vector

class JoystickInputHandler(
  looper: Looper,
  width: Int,
  height: Int,
  val joystickRadius: Float,
) : Handler(looper) {
  val transformMatrix: Matrix = Matrix.normalizedInputMatrix(width, height)
  var touchPoint: Vector? = null
  var moveVec: Vector? = null
  var movePoint: Vector? = null

  override fun handleMessage(msg: Message) {
    when (val event = msg.data.getParcelable("event", InputEvent::class.java)) {
      InputEvent.Released -> {
        touchPoint = null
        moveVec = null
        movePoint = null
      }
      is InputEvent.Moved -> {
        Log.d("JoystickInputHandler", "Moved (${event.x},${event.y})")
        val transformed = transformMatrix * Vector(event.x, event.y, 1.0f)
        moveVec = (transformed - touchPoint!!).project(2).homogeneous()
        if (moveVec!!.project(2).length() > joystickRadius) {
          moveVec = (moveVec!!.project(2).normalized() * joystickRadius).homogeneous()
        }
        movePoint = (moveVec!! + touchPoint!!).project(2).homogeneous()
      }

      is InputEvent.Pressed -> {
        Log.d("JoystickInputHandler", "Pressed (${event.x},${event.y})")
        touchPoint = transformMatrix * Vector(event.x, event.y, 1.0f)
      }

      null -> {
        touchPoint = null
        moveVec = null
        movePoint = null
      }
    }
  }
}
