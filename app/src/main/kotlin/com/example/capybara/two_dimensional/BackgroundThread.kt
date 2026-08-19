package com.example.capybara.two_dimensional

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import android.view.Surface
import androidx.core.graphics.withSave

class BackgroundThread(private val surface: Surface, val x: Int, val y: Int): Thread() {
    var handler: Handler? = null
    var choreographer: Choreographer? = null
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
        }
        surface.unlockCanvasAndPost(canvas)
        choreographer?.postFrameCallback(::doFrame)
    }

    override fun run() {
        Looper.prepare()
        Looper.myLooper()?.let { handler = Handler(it) }
        choreographer = Choreographer.getInstance()
        choreographer?.postFrameCallback(::doFrame)
        Looper.loop()
    }

    fun quit() {
        handler?.looper?.quitSafely()
    }
}