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

@Composable
fun MainMenu(stack: MutableList<Routes>) {
  val activity = LocalActivity.current
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Button(
      onClick = {
        stack.add(Routes.TwoD(0.5f, 0.5f))
      }
    ) {
      Text(text = "Start")
    }
    Button(
      onClick = {
        stack.add(Routes.Coordinates)
      }
    ) {
      Text(text = "Coordinates")
    }
    Button(
      onClick = {
        stack.add(Routes.Options)
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
