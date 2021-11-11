package com.realworld.jcompose.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BlankScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Hello")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) {
        content()
    }
}