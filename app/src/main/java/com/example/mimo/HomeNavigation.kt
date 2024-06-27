package com.example.mimo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lockscreen.LockScreen
import com.example.mimo.component.BottomNavigation
import com.example.mimo.screen.ChatPage
import com.example.mimo.screen.Loginpage
import com.example.mimo.screen.MainPage
import com.example.mimo.screen.alarm.AlarmSettingScreen
import com.example.mimo.screen.alarm.BellScreen
import com.example.mimo.screen.alarm.UnLockScreen
import com.example.mimo.screen.alarm.WakeUpPage
import com.example.mimo.screen.chat.DreamScreen
import com.example.mimo.screen.diary.AddDiaryScreen
import com.example.mimo.screen.diary.DiaryScreen
import com.example.mimo.screen.diary.model.DiaryState
import com.example.mimo.screen.diary.model.DiaryViewModel
import com.example.mimo.screen.setting.AccountScreen
import com.example.mimo.screen.setting.SettingScreen
import com.example.mimo.screen.setting.TvConnectScreen


@Composable
fun HomeNavigation(state:DiaryState, viewModel:DiaryViewModel, mainNavController: NavController, destination: String, index: Int){
    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController, startIndex = index)
        }
    ) { inPadding ->
        NavHost(
            modifier = Modifier.padding(inPadding),
            navController = navController,
            startDestination = destination
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
                    onEvent = viewModel::onEvent,
                )
            }
            composable("AddNoteScreen") {
                AddDiaryScreen(
                    state = state,
                    navController = navController,
                    onEvent = viewModel::onEvent
                )
            }

            composable("AlarmSettingScreen") {
                AlarmSettingScreen(mainNavController = mainNavController)
            }
            composable("TvConnectScreen") {
                TvConnectScreen(navController = navController)
            }
            composable("SettingPage") {
                SettingScreen(navController = navController)
            }
            composable("AccountPage") {
                AccountScreen(mainNavController = mainNavController)
            }

            composable("BellPage") {
                BellScreen(navController = navController)
            }
        }
    }
}