package com.example.capybara.two_dimensional

import android.annotation.SuppressLint
import android.content.Context
import android.os.Message
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

/*
   https://developer.android.com/reference/android/view/SurfaceView
   The Surface will be created for you while the SurfaceView's window is visible;
   you should implement SurfaceHolder.Callback.surfaceCreated and SurfaceHolder.Callback.surfaceDestroyed
   to discover when the Surface is created and destroyed as the window is shown and hidden.
*/
class TwoDView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
  lateinit var worker: BackgroundThread
  var x: Int = 0
  var y: Int = 0
  var firstPointerId: Int? = null

  init {
    holder.addCallback(this)
  }

  override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

  override fun surfaceCreated(holder: SurfaceHolder) {
    worker = BackgroundThread(holder.surface, x, y)
    worker.start()
  }

  override fun surfaceDestroyed(holder: SurfaceHolder) {
    worker.quit()
    worker.join()
  }

  /*
   https://developer.android.com/develop/ui/views/touch-and-input/gestures/multi
  */
  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent?): Boolean {
    val msg = Message.obtain()
    when (event?.action) {
      MotionEvent.ACTION_DOWN -> {
        firstPointerId = event.getPointerId(0)
        msg.data.putParcelable("event", InputEvent.Pressed(event.x, event.y))
        worker.handler?.dispatchMessage(msg)
        return true
      }
      // https://developer.android.com/reference/android/view/MotionEvent#batching
      MotionEvent.ACTION_MOVE -> {
        if (firstPointerId != null && event.getPointerId(event.actionIndex) == firstPointerId) {
          msg.data.putParcelable("event", InputEvent.Moved(event.x, event.y))
          worker.handler?.dispatchMessage(msg)
        }
        return true
      }
      MotionEvent.ACTION_POINTER_UP -> {
        if (firstPointerId != null && event.getPointerId(event.actionIndex) == firstPointerId) {
          msg.data.putParcelable("event", InputEvent.Released)
          worker.handler?.dispatchMessage(msg)
          firstPointerId = null
        }
        return true
      }
      MotionEvent.ACTION_UP -> {
        if (firstPointerId != null) {
          msg.data.putParcelable("event", InputEvent.Released)
          worker.handler?.dispatchMessage(msg)
          firstPointerId = null
        }
      }
    }
    return super.onTouchEvent(event)
  }
}
