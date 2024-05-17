package com.example.mimo.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.component.BottomNavigation

@Composable
fun MainPage(navController: NavController) {
        val text = remember { "메인페이지입니다." } // 상태 관리를 위해 remember를 사용하여 상태를 유지합니다.
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = text)
        }

}

@Preview
@Composable
fun MainPagePreview() {
    // Create a fake NavController for preview purposes
    val navController = rememberNavController()
    MainPage(navController = navController)
}
