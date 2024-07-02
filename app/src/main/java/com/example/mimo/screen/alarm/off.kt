package com.example.mimo.screen.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun OffScreen(navController:NavController){
    Surface(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
    }
}