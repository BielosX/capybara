package com.example.capybara

import kotlinx.serialization.Serializable

@Serializable object MainMenuRoute

@Serializable object CoordinatesRoute

@Serializable data class TwoDRoute(val x: Float, val y: Float)

@Serializable object OptionsRoute
