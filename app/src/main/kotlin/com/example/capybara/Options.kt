package com.example.capybara

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Options() {
  Scaffold(
    floatingActionButton = {},
    topBar = {
      Text("Options")
    },
  ) { innerPadding ->
    Text(modifier = Modifier.padding(innerPadding), text = "Options")
  }
}
