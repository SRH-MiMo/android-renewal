package com.example.mimo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // 수정: Modifier import 추가
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mimo.component.BottomNavigation
import com.example.mimo.screen.ChatPage
import com.example.mimo.screen.DiaryPage
import com.example.mimo.screen.MainPage
import com.example.mimo.screen.SettingsPage // 수정: SettingsPage import 추가
import com.example.mimo.ui.theme.MimoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MimoTheme {
                Nav()
            }
        }
    }
}

@Composable
fun Nav() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "MainPage",
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable("MainPage") {
                MainPage(navController = navController)
            }
            composable("ChatPage") {
                ChatPage(navController = navController)
            }
            composable("SettingPage") { // 수정: 목적지 이름 변경
                SettingsPage(navController = navController)
            }
            composable("DiaryPage") {
                DiaryPage(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MimoTheme {
        Nav()
    }
}
