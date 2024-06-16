package com.example.mimo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lockscreen.LockScreen
import androidx.navigation.compose.rememberNavController

class LockScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LockScreen(rememberNavController())
        }
    }
}
