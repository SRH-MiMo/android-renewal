package com.example.mimo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mimo.R

data class Nav(
    val title: String,
    val iconRes: Int // Resource ID를 사용
)

val items = listOf(
    Nav(
        title = "Home",
        iconRes = R.drawable.home
    ),
    Nav(
        title = "Save",
        iconRes = R.drawable.save
    ),
    Nav(
        title = "Chat",
        iconRes = R.drawable.chat
    ),
    Nav(
        title = "Setting",
        iconRes = R.drawable.setting
    )
)

@Preview
@Composable
fun BottomNavigation() {
    NavigationBar(
        containerColor = Color.Black // NavigationBar의 배경색 설정
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == 0,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        modifier = Modifier
                            .width(35.dp)
                            .height(35.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White, // 선택된 아이콘의 색상
                    unselectedIconColor = Color.White // 선택되지 않은 아이콘의 색상
                ),
            )
        }
    }
}
