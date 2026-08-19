package com.example.capybara.two_dimensional

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import kotlinx.parcelize.Parcelize

class TwoDActivity: ComponentActivity() {

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
        val view = TwoDView(this)
        view.x = input.x
        view.y = input.y
        setContentView(view)
    }
}