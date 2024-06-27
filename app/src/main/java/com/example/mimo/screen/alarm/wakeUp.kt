package com.example.mimo.screen.alarm

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.R
import com.example.mimo.component.BottomNavigation
import com.example.mimo.screen.diary.model.DiaryEvent
import com.example.mimo.ui.theme.PurpleEnd
import com.example.mimo.ui.theme.PurpleStart
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun WakeUpPage(navController: NavController) {
    Scaffold(
    ) { inPadding ->
        Column(
            modifier = Modifier.padding(inPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "좋은 밤 보내셨나요?\n어제 꾼 꿈을 해몽해 보아요!",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(200.dp))
            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(90.dp)
                    .padding(16.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PurpleStart, PurpleEnd)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        navController.navigate("ChatNested")
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "꿈 해몽하러 가기",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White, // Assuming white text color
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WakeUPpagePreview() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212) // 어두운 배경색
    ) {
        WakeUpPage(navController = navController)
    }
}
