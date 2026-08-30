package com.example.capybara

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.capybara.coordinates.CoordinatesActivity
import com.example.capybara.two_dimensional.TwoDActivity

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.systemBarsBehavior =
      WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    actionBar?.hide()
    setContent {
      MaterialTheme {
        MainActivityContent()
      }
    }
  }
}

@Composable
fun MainActivityContent() {
  val activity = LocalActivity.current
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Button(
      onClick = {
        val intent =
          Intent(activity, TwoDActivity::class.java).apply {
            putExtra("input", TwoDActivity.Input(200, 1000))
          }
        activity?.startActivity(intent)
      }
    ) {
      Text(text = "Start")
    }
    Button(
      onClick = {
        val intent = Intent(activity, CoordinatesActivity::class.java)
        activity?.startActivity(intent)
      }
    ) {
      Text(text = "Coordinates")
    }
    Button(onClick = {}) {
      Text(text = "Options")
    }
    Button(
      onClick = {
        activity?.finishAndRemoveTask()
      }
    ) {
      Text(text = "Exit")
    }
  }
}
