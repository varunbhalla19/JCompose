package com.realworld.jcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.realworld.jcompose.ui.components.MyApp
import com.realworld.jcompose.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTestTheme {
                MyApp()
            }
        }
    }
}
