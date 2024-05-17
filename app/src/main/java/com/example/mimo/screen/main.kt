package com.example.mimo.screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mimo.component.BottomNavigation

@Preview
@Composable
fun MainPage() {
    Scaffold(
        bottomBar = { BottomNavigation() }
    ) { padding ->

    }
}