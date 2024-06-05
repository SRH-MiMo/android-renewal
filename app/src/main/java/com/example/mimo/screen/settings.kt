package com.example.mimo.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.component.BottomNavigation
import com.example.mimo.supabase
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "민재야 세팅기능 만들어",
                fontSize = 40.sp
            )

            LogoutButton(navController = navController)
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

@Composable
fun LogoutButton(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {
        coroutineScope.launch {
            try {
                supabase.auth.signOut()

                Toast.makeText(context, "로그아웃 됌", Toast.LENGTH_SHORT).show()

                navController.navigate("MainPage")
            } catch (e: RestException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

            }
        }
    }) {
        Text("Logout")
    }
}