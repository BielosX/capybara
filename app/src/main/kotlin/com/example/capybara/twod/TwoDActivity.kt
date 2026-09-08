package com.example.capybara.twod

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.parcelize.Parcelize

class TwoDActivity : ComponentActivity() {

  /*
     https://developer.android.com/kotlin/parcelize
     https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.parcelize
  */
  @Parcelize data class Input(val x: Float = 0.0f, val y: Float = 0.0f) : Parcelable

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.systemBarsBehavior =
      WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    actionBar?.hide()
    var input = Input(0.0f, 0.0f)
    intent.extras.let { extras ->
      extras?.getParcelable<Input>("input", Input::class.java)?.let {
        input = it
      }
    }
    val view = TwoDView(this)
    view.initX = input.x
    view.initY = input.y
    setContentView(view)
  }
}
