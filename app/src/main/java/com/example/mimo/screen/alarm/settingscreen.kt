    package com.example.mimo.screen.alarm

    import android.os.Bundle

    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.text.BasicTextField
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.focus.onFocusChanged
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.input.TextFieldValue
    import androidx.compose.ui.text.input.ImeAction
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.graphics.Brush
    import androidx.compose.ui.res.painterResource
    import androidx.navigation.NavController
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.rememberNavController
    import com.example.mimo.R

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                val navController = rememberNavController()
                AlarmSettingScreen(navController = navController)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AlarmSettingScreen(navController: NavController) {
        var hour by remember { mutableStateOf(7) }
        var minute by remember { mutableStateOf(0) }
        var period by remember { mutableStateOf("오전") }
        var comment by remember { mutableStateOf(TextFieldValue("")) }
        var isAlarmSaved by remember { mutableStateOf(false) } // 저장 버튼이 눌렸는지 여부를 추적하는 상태

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212)) // 배경색 설정
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 상단 타이틀
            Text(
                text = "알람 설정",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 시간 선택 섹션
            Text(
                text = "기상시간 설정",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            AlarmTimePicker(
                hour = hour,
                minute = minute,
                period = period,
                onHourChange = { hour = it },
                onMinuteChange = { minute = it },
                onPeriodChange = { period = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 격언 입력 섹션
            Text(
                text = "격언 설정",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            CommentInput(
                comment = comment,
                onCommentChange = { comment = it }
            )

            Text(
                text = "설정하신 격언은 화면 잠금 해제에 사용됩니다",
                fontSize = 12.sp,
                color = Color.Magenta,
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            // 저장하기 버튼
            Button(
                onClick = {
                    // 저장하기 버튼을 클릭하면 알람 설정을 저장하고, 잠금 화면으로 전환됩니다.
                    isAlarmSaved = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta
                )
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(Color.DarkGray, shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "오전",
                    color = if (period == "오전") Color.White else Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { onPeriodChange("오전") }
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "오후",
                    color = if (period == "오후") Color.White else Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { onPeriodChange("오후") }
                        .padding(vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = String.format("%02d", hour),
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = ":",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = String.format("%02d", minute),
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CommentInput(comment: TextFieldValue, onCommentChange: (TextFieldValue) -> Unit) {
        var isHintDisplayed by remember { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray, shape = MaterialTheme.shapes.medium)
                .padding(8.dp)
        ) {
            BasicTextField(
                value = comment,
                onValueChange = {
                    onCommentChange(it)
                    isHintDisplayed = it.text.isEmpty()
                },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = if (isHintDisplayed) 16.dp else 0.dp)
                    .onFocusChanged {
                        isHintDisplayed = !it.isFocused && comment.text.isEmpty()
                    }
            )

            if (isHintDisplayed) {
                Text(
                    text = "한마디 격언 입력",
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }



    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        val navController = rememberNavController()
        AlarmSettingScreen(navController = navController)
    }
