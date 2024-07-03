package com.example.lockscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.mimo.R

@Composable
fun LockScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "화면 잠금 중",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.sleepmoon),
                contentDescription = null,
                modifier = Modifier.size(300.dp).clickable { navController.navigate("BellScreen") }
            )
            Spacer(modifier = Modifier.height(50.dp))
            SlideToUnlock(navController)
        }
    }
}

@Composable
fun SlideToUnlock(navController: NavController) {
    var offsetX by remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .width(300.dp)
            .height(50.dp)
            .background(Color.Gray, shape = androidx.compose.foundation.shape.RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "밀어서 격언 입력하기",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), 0) }
                .size(50.dp)
                .background(Color.Magenta, shape = androidx.compose.foundation.shape.RoundedCornerShape(25.dp))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX = (offsetX + dragAmount).coerceIn(0f, with(density) { 300.dp.toPx() - 50.dp.toPx() })

                        // 슬라이드가 끝까지 되었을 때 네비게이션
                        if (offsetX >= with(density) { 300.dp.toPx() - 50.dp.toPx() }) {
                            navController.navigate("UnLockScreen")
                        }
                    }
                }
                .zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LockscreenPreview() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212) // 어두운 배경색
    ) {
        LockScreen(navController = navController)
    }
}
