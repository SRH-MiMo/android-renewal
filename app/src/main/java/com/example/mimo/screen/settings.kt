package com.example.mimo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.component.BottomNavigation

@Composable
fun SettingsPage(navController: NavController) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "민재야 세팅기능 만들어",
                    fontSize = 40.sp
                )
            }
        }
}

@Preview
@Composable
fun SettingView() {
    // Create a fake NavController for preview purposes
    val navController = rememberNavController()
    SettingsPage(navController = navController)
}
