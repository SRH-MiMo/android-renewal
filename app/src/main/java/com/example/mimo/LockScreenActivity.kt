package com.example.mimo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.lockscreen.LockScreen
import com.example.mimo.ui.theme.MimoTheme

class LockScreenActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setShowWhenLocked(true)

        setContent {
            MimoTheme {
                LockActivityScreen()

            }
        }
    }
}

@Composable
fun LockActivityScreen(){
    val navController = rememberNavController()
    LockScreen(navController = navController)
}