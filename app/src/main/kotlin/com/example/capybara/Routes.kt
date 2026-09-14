package com.example.capybara

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
  object MainMenu : Routes()

  object Coordinates : Routes()

  object Options : Routes()

  data class TwoD(val x: Float, val y: Float) : Routes()
}
