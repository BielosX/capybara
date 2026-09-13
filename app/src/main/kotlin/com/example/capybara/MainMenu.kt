package com.example.capybara

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainMenu(nav: NavController) {
  val activity = LocalActivity.current
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Button(
      onClick = {
        nav.navigate(TwoDRoute(0.5f, 0.5f))
      }
    ) {
      Text(text = "Start")
    }
    Button(
      onClick = {
        nav.navigate(CoordinatesRoute)
      }
    ) {
      Text(text = "Coordinates")
    }
    Button(
      onClick = {
        nav.navigate(OptionsRoute)
      }
    ) {
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
