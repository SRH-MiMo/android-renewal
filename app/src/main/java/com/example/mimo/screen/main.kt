package com.example.mimo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.component.BottomNavigation
import com.example.mimo.component.TopBar

@Composable
fun MainPage(navController: NavController) {
        Surface(
            color = Color.Black,
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(name = "미라클 모닝 시작하기")
        }

}

@Preview
@Composable
fun MainPagePreview() {
    // Create a fake NavController for preview purposes
    val navController = rememberNavController()
    MainPage(navController = navController)
}
