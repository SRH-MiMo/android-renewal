package com.example.mimo.screen.alarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmSettingScreen()
        }
    }
}

@Composable
fun AlarmSettingScreen() {
    var hour by remember { mutableStateOf(7) }
    var minute by remember { mutableStateOf(0) }
    var period by remember { mutableStateOf("오전") }
    var comment by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "알람 설정",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        AlarmTimePicker(
            hour = hour,
            minute = minute,
            period = period,
            onHourChange = { hour = it },
            onMinuteChange = { minute = it },
            onPeriodChange = { period = it }
        )
        Spacer(modifier = Modifier.height(32.dp))
        CommentInput(
            comment = comment,
            onCommentChange = { comment = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
           colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta)
        ) {
            Text(text = "저장하기", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun AlarmTimePicker(
    hour: Int,
    minute: Int,
    period: String,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit,
    onPeriodChange: (String) -> Unit
) {
    // 간단한 시간 선택기 구현
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.DarkGray)
            .padding(16.dp)
    ) {
        Text(
            text = period,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .clickable { onPeriodChange(if (period == "오전") "오후" else "오전") }
                .padding(8.dp)
        )
        Text(
            text = String.format("%02d : %02d", hour, minute),
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun CommentInput(comment: TextFieldValue, onCommentChange: (TextFieldValue) -> Unit) {
    TextField(
        value = comment,
        onValueChange = onCommentChange,
        placeholder = { Text(text = "한마디 격언 입력", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(8.dp),
        textStyle = TextStyle(color = Color.White),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White
        )

    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlarmSettingScreen()
}

