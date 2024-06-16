package com.example.mimo.screen.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


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
                Toast.makeText(context, period + " " + hour + " " + minute, Toast.LENGTH_SHORT)
                    .show()
                isAlarmSaved = true
                addAlarm(context, hour, minute, period, comment, navController)
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

@RequiresApi(Build.VERSION_CODES.S)
private fun canScheduleExactAlarms(context: Context): Boolean {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    return alarmManager.canScheduleExactAlarms()
}

@RequiresApi(Build.VERSION_CODES.S)
private fun requestExactAlarmPermission(context: Context) {
    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
    context.startActivity(intent)
}

fun addAlarm(
    context: Context,
    hour: Int,
    minute: Int,
    period: String,
    comment: TextFieldValue,
    navController: NavController
) {
    Toast.makeText(context, "알람 예약 함수 실행됨", Toast.LENGTH_SHORT).show()
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pIntent = PendingIntent.getBroadcast(
        context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val cal = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, if (period == "오전") hour % 12 else (hour % 12) + 12)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        if (before(Calendar.getInstance())) {
            add(Calendar.DAY_OF_MONTH, 1)
        }
    }
    Toast.makeText(context, "알람 일정 등록", Toast.LENGTH_SHORT).show()
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (canScheduleExactAlarms(context)) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    cal.timeInMillis,
                    pIntent
                )
                Toast.makeText(context, "정확한 알람이 설정됨", Toast.LENGTH_SHORT).show()
                navController.navigate("BellPage")

            } else {
                requestExactAlarmPermission(context)
                Toast.makeText(context, "정확한 알람 설정을 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                cal.timeInMillis,
                pIntent
            )
            Toast.makeText(context, "정확한 알람이 설정됨", Toast.LENGTH_SHORT).show()

            // 화면 꺼지고 키면 락페이지 되어있는거

            // 화면 켜졌을때 가장 마지막 실행 되어있는거 이걸로 해주기
            navController.navigate("BellPage")
        }
    } catch (e: SecurityException) {
        Toast.makeText(context, "알람 설정 권한이 필요합니다: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            // 넘어가는 로직 만드셈

            val powerManager = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
            val wakeLock = powerManager.newWakeLock(
                PowerManager.FULL_WAKE_LOCK or
                        PowerManager.ACQUIRE_CAUSES_WAKEUP or
                        PowerManager.ON_AFTER_RELEASE, "My:Tag"
            )
            // 켜서 전원 켜버리기
            wakeLock.acquire(5000)

            Toast.makeText(context, "파워매니져 슛", Toast.LENGTH_SHORT).show()

            //파워매니져 off
            wakeLock.release()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    AlarmSettingScreen(navController = navController)
}
