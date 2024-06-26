package com.example.mimo

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier // 수정: Modifier import 추가
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mimo.component.BottomNavigation
import com.example.mimo.data.Diary.DiariesDatabase
import com.example.mimo.screen.ChatPage
import com.example.mimo.screen.Loginpage
import com.example.mimo.screen.MainPage
import com.example.mimo.screen.alarm.AlarmSettingScreen
import com.example.mimo.screen.chat.DreamScreen
import com.example.mimo.screen.diary.AddDiaryScreen
import com.example.mimo.screen.diary.DiaryScreen
import com.example.mimo.screen.diary.model.DiaryState
import com.example.mimo.screen.diary.model.DiaryViewModel
import com.example.mimo.screen.setting.AccountScreen
import com.example.mimo.screen.setting.SettingScreen
import com.example.mimo.screen.setting.TvConnectScreen
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

    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Nav(state: DiaryState, viewModel: DiaryViewModel) {
    val navController = rememberNavController()

    // 유저 로그인 정보 (비 로그인시 null)
    val session = supabase.auth.currentSessionOrNull()

    //첫 시작 위치 결정
    val startDestination = if (session?.user != null) {
        "MainPage"
    } else {
        "LoginPage"
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable("MainPage") {
                MainPage(navController = navController)
            }
            composable("ChatPage") {
                ChatPage(navController = navController)
            }
            composable("TalkScreen") {
                DreamScreen(navController = navController)
            }
            composable("DiaryPage") {
                DiaryScreen(
                    state = state,
                    navController = navController,
                    onEvent = viewModel::onEvent
                )
            }
            composable("AddNoteScreen") {
                AddDiaryScreen(
                    state = state,
                    navController = navController,
                    onEvent = viewModel::onEvent
                )
            }
            composable("LoginPage") {
                Loginpage(navController = navController)
            }
            composable("AlarmSettingScreen") {
                AlarmSettingScreen(navController = navController)
            }
            composable("TvConnectScreen") {
                TvConnectScreen(navController = navController)
            }
            composable("SettingPage") {
                SettingScreen(navController = navController)
            }
            composable("AccountPage") {
                AccountScreen(navController = navController)
            }
        }
    }
}
