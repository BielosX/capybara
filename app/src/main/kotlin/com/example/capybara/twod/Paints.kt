package com.example.capybara.twod

import android.graphics.Color
import android.graphics.Paint

val circlePaint =
  Paint().apply {
    isAntiAlias = true
    color = Color.RED
    style = Paint.Style.FILL
  }

val outlinePaint =
  Paint().apply {
    isAntiAlias = true
    color = Color.GRAY
    style = Paint.Style.STROKE
    strokeWidth = 4f
  }

val analogPaint =
  Paint().apply {
    isAntiAlias = true
    color = Color.GRAY
    style = Paint.Style.FILL
  }
