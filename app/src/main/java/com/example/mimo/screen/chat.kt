package com.example.mimo.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.R
import com.example.mimo.component.BottomNavigation
import com.example.mimo.component.TopBar
import com.example.mimo.ui.theme.PurpleEnd
import com.example.mimo.ui.theme.PurpleStart

@Composable
fun ChatPage(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TopBar(name = "채팅 꿈 해몽")
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp),
                painter = painterResource(id = R.drawable.luna),
                contentDescription = "달사진"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "꿈 해몽 AI '루나'",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "꿈 내용을 입력해 꿈 해몽을 받아보세요!\n" +
                        "AI 루나가 당신의 꿈을 해석해드립니다!",
                color = Color(0xB2FFFFFF),
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(40.dp))

            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .width(350.dp)
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
                        navController.navigate("TalkScreen")
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "시작하기",
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

@Preview
@Composable
fun ChatPreview() {
    // Create a fake NavController for preview purposes
    val navController = rememberNavController()
    ChatPage(navController = navController)
}
