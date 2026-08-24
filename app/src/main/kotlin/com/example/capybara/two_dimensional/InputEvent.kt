package com.example.capybara.two_dimensional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
   https://developer.android.com/kotlin/parcelize
*/
@Parcelize
sealed class InputEvent : Parcelable {
  data object Released : InputEvent()

  class Pressed(val x: Float, val y: Float) : InputEvent()

  class Moved(val x: Float, val y: Float) : InputEvent()
}
