package com.example.mimo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lockscreen.LockScreen
import androidx.room.Room
import com.example.mimo.data.Diary.DiariesDatabase
import com.example.mimo.screen.Loginpage
import com.example.mimo.screen.alarm.BellScreen
import com.example.mimo.screen.alarm.UnLockScreen
import com.example.mimo.screen.alarm.WakeUpPage
import com.example.mimo.screen.diary.model.DiaryState
import com.example.mimo.screen.diary.model.DiaryViewModel
import com.example.mimo.ui.theme.MimoTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = BuildConfig.SUPABASE_URL, //BuildConfig.SUPABASE_URL,
    supabaseKey = BuildConfig.SUPABASE_ANON_KEY, //BuildConfig.SUPABASE_ANON_KEY
) {
    install(Auth)
    install(Postgrest)
}

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            DiariesDatabase::class.java,
            "diaries.db"
        ).build()
    }

    private val viewModel by viewModels<DiaryViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DiaryViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        enableEdgeToEdge()
        setContent {
            MimoTheme {
                val state by viewModel.state.collectAsState()
                Nav(state, viewModel)
            }
        }
    }
}

@Composable
fun Nav(state: DiaryState, viewModel: DiaryViewModel) {
    val navController = rememberNavController()

    // 유저 로그인 정보 (비 로그인시 null)
    val session = supabase.auth.currentSessionOrNull()

    //첫 시작 위치 결정
    val startDestination = if (session?.user != null) {
        "MainHost"
    } else {
        "LoginPage"
    }


    NavHost(navController = navController, startDestination = startDestination) {
        composable("MainHost") {
            HomeNavigation(state, viewModel, mainNavController = navController, destination = "MainPage", index = 0)
        }
        composable("ChatNested") {
            HomeNavigation(state, viewModel, mainNavController = navController, destination = "ChatPage", index = 2)
        }

        composable("UnLockScreen") {
            UnLockScreen(navController = navController, context = LocalContext.current)
        }
        composable("WakeUp") {
            WakeUpPage(navController = navController)
        }
        composable("LoginPage") {
            Loginpage(navController = navController)
        }
        composable("LockScreen") {
            LockScreen(navController = navController)
        }
        composable("BellPage") {
            BellScreen(navController = navController)
        }
    }
}

