package com.example.mimo.screen.alarm

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lockscreen.LockScreen
import com.example.mimo.ui.theme.PurpleEnd
import com.example.mimo.ui.theme.PurpleStart


@Composable
fun UnLockScreen(navController: NavController, context: Context) {
    var password by remember { mutableStateOf("") } // 비밀번호를 저장하는 상태 변수
    val keyword = "신개념 자살 종용쇼"
    Scaffold(

    ) { inPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inPadding),
            contentAlignment = Alignment.Center
        ) { // Box에 contentAlignment을 사용하여 가운데 정렬
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Text(
                    text = "격언 입력해서 잠금 해제",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                TextField(
                    value = password, // Use the password state directly
                    onValueChange = { newPassword -> password = newPassword },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    placeholder = {
                        Text(
                            text = "'${keyword}'를 입력해주세요.",
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                    },
                    textStyle = TextStyle(
                        color = Color.White
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            if (password == keyword) {
                                navController.navigate("WakeUp")
                            } else {
                                Toast.makeText(context, "$keyword 가 아닙니다", Toast.LENGTH_SHORT).show()
                            }
                        }, // 버튼 클릭 시 비밀번호 전달
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp) // 버튼의 높이를 조정합니다.
                            .padding(horizontal = 32.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            "잠금 해제"
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun UnLockScreenPreview() {
    val navController = rememberNavController()
    UnLockScreen(navController = navController, context = LocalContext.current)

}