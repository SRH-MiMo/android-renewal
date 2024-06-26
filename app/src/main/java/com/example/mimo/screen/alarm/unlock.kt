package com.example.mimo.screen.alarm

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController


@Composable
fun LockScreenUI(onUnlock: (String) -> Unit) {
    var password by remember { mutableStateOf("") } // 비밀번호를 저장하는 상태 변수

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { // Box에 contentAlignment을 사용하여 가운데 정렬
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
                value = TextFieldValue(password),
                onValueChange = { newPassword -> password = newPassword.text },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                placeholder = {
                    Text(
                        text = "설정하신 격언을 입력해주세요",
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                },
                textStyle = TextStyle(
                    color = Color.White
                ),

                )


            Button(
                onClick = { onUnlock(password) }, // 버튼 클릭 시 비밀번호 전달
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // 버튼의 높이를 조정합니다.
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD11091),
                    contentColor = Color.White
                )
            ) {
                Text("잠금 해제")
            }
        }
    }
}


@Preview
@Composable
fun lockScreenPreview() {
    LockScreenUI(onUnlock = {})
}