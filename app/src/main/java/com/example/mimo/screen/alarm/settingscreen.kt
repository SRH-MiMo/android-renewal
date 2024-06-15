package com.example.mimo.screen.alarm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSettingScreen(navController: NavController) {

    val context = LocalContext.current

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
                Toast.makeText(context, period + " "+ hour + " " + minute, Toast.LENGTH_SHORT).show()
                navController.navigate("LockScreen")
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
    val hours = (1..12).map { it.toString().padStart(2, '0') }
    val minutes = (0..59).map { it.toString().padStart(2, '0') }
    val periods = listOf("오전", "오후")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.DarkGray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Picker(
            items = periods,
            selectedIndex = periods.indexOf(period),
            onItemSelected = { onPeriodChange(periods[it]) },
            modifier = Modifier.weight(1f)
        )
        Picker(
            items = hours,
            selectedIndex = if (hour % 12 == 0) 11 else (hour % 12) - 1,
            onItemSelected = { onHourChange(if (period == "오전") it + 1 else it + 13) },
            modifier = Modifier.weight(1f)
        )
        Picker(
            items = minutes,
            selectedIndex = minute,
            onItemSelected = { onMinuteChange(it) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun Picker(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableStateOf(selectedIndex) }
    val listState = rememberLazyListState()

    LaunchedEffect(currentIndex) {
        listState.animateScrollToItem(currentIndex)
    }

    LazyColumn(
        state = listState,
        modifier = modifier.height(150.dp)
    ) {
        itemsIndexed(items) { index, item ->
            Text(
                text = item,
                color = if (index == currentIndex) Color.White else Color.Gray,
                fontSize = if (index == currentIndex) 24.sp else 20.sp,
                fontWeight = if (index == currentIndex) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        currentIndex = index
                        onItemSelected(index)
                    }
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
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
