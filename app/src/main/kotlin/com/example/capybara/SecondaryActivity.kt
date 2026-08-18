package com.example.capybara

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize

class SecondaryActivity: ComponentActivity() {

    /*
        https://developer.android.com/kotlin/parcelize
        https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.parcelize
     */
    @Parcelize
    data class Input(val x: Int = 0, val y: Int = 0) : Parcelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var input = Input(0, 0)
        intent.extras.let { extras ->
            extras?.getParcelable<Input>("input", Input::class.java)?.let {
                input = it
            }
        }
        setContent {
            MaterialTheme {
                SecondaryActivityContent(input.x, input.y)
            }
        }
    }
}

@Composable
fun SecondaryActivityContent(x: Int, y: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello $x $y")
    }
}