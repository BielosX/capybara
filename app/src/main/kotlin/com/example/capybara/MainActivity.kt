package com.example.capybara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capybara.coordinates.CoordinatesActivity
import com.example.capybara.twod.TwoDActivity

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
        App()
      }
    }
  }
}

@Composable
fun App() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = MainMenuRoute) {
    composable<MainMenuRoute> { MainMenu(navController) }
    composable<OptionsRoute> { Options() }
    activity<CoordinatesRoute> {
      activityClass = CoordinatesActivity::class
    }
    activity<TwoDRoute> {
      activityClass = TwoDActivity::class
    }
  }
}
