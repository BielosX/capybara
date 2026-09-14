package com.example.capybara

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
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

/*
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
 */

@Composable
fun App() {
  val activity = LocalActivity.current
  val backStack = remember { mutableStateListOf<Routes>(Routes.MainMenu) }
  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider =
      entryProvider {
        entry<Routes.MainMenu> { MainMenu(backStack) }
        entry<Routes.Options> { Options() }
        entry<Routes.Coordinates> {
          activity?.startActivity(Intent(activity, CoordinatesActivity::class.java))
        }
        entry<Routes.TwoD> { route ->
          val intent =
            Intent(activity, TwoDActivity::class.java).apply {
              putExtra("x", route.x)
              putExtra("y", route.y)
            }
          activity?.startActivity(intent)
        }
      },
  )
}
