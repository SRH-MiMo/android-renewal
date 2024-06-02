package com.example.mimo.screen.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mimo.R

class watchselectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF121212) // 어두운 배경색
            ) {
                watchselectActivity()
            }
        }
    }
}

@Composable
fun WatchSelectScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "워치 연결하기",
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "디바이스 선택",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.rendering),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Crop
            )
        }

        Divider(color = Color.Gray, thickness = 1.dp)

        Spacer(modifier = Modifier.height(31.dp)) // 간격 추가

        val deviceList = listOf("WATCH", "JMIN's APPLE WATCH")

        deviceList.forEach { device ->
            WatchSelectOption(device)
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun WatchSelectOption(deviceName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { /* Handle device selection */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.watch), // 워치 아이콘으로 변경
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = deviceName,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WatchSelectScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212) // 어두운 배경색
    ) {
        WatchSelectScreen()
    }
}

