package com.example.mimo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mimo.Nav
import com.example.mimo.R
import com.example.mimo.data.Nav


val items = listOf(
    Nav(
        title = "Home",
        route = "MainPage",
        iconRes = R.drawable.home
    ),
    Nav(
        title = "Diary",
        route = "DiaryPage",
        iconRes = R.drawable.save
    ),
    Nav(
        title = "Chat",
        route = "ChatPage",
        iconRes = R.drawable.chat
    ),
    Nav(
        title = "Setting",
        route = "SettingPage",
        iconRes = R.drawable.setting
    )
)

@Composable
fun BottomNavigation(navController: NavController) {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    NavigationBar(
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    navController.navigate(item.route)
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.title,
                            tint = Color.White, // 아이콘은 항상 하얀색으로 표시
                            modifier = Modifier
                                .size(35.dp)
                        )
                        if (selectedIndex == index) {
                            Box(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(3.dp)
                                    .background(Color(0xFFAE00BD)) // 하단 인디케이터 색상
                            )
                        }
                    }
                },
//                colors = NavigationBarItemDefaults.colors(
//                   indicatorColor = Color.Transparent, // 선택된 항목의 배경색을 투명하게 설정
//                    selectedIconColor = Color.White, // 선택된 아이콘의 색상
//                    unselectedIconColor = Color.White, // 선택되지 않은 아이콘의 색상
//                )
            )
        }
    }
}