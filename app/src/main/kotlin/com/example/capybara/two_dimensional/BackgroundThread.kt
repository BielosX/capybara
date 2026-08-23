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

class BackgroundThread(private val surface: Surface, val x: Int, val y: Int): Thread(), Handler.Callback {
    var handler: Handler? = null
    var choreographer: Choreographer? = null
    var touchPoint: Point? = null
    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        style = Paint.Style.FILL
    }

    fun doFrame(timeNanos: Long) {
        val canvas = surface.lockCanvas(null)
        canvas.withSave {
            drawColor(Color.WHITE)
            drawRect(Rect(x, y, x+100, y+100), paint)
            if (touchPoint != null) {
                drawCircle(touchPoint!!.x.toFloat(), touchPoint!!.y.toFloat(), 100f, paint)
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
        if (!msg.data.isEmpty) {
            touchPoint = Point(msg.data.getInt("x"), msg.data.getInt("y"))
        } else {
            touchPoint = null
        }
        return true
    }
}