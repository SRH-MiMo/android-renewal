package com.example.mimo.screen.setting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.mimo.R

class DeviceSelectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF121212) // 어두운 배경색
            ) {
                DeviceSelectScreen()
            }
        }
    }
}

@Composable
fun DeviceSelectScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "TV 연결하기",
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

        val deviceList = listOf("JMIN TV", "3-6 TV", "tkffuwnj", "a12sjs29", "국재윤 명칭이")

        deviceList.forEach { device ->
            DeviceButton(device)
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

@Composable
fun DeviceButton(deviceName: String) {
    Button(
        onClick = { /* Handle device selection */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F1F1F)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Adjust padding if needed
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.tv), // 디바이스 아이콘
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
}

@Preview(showBackground = true)
@Composable
fun DeviceSelectScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212) // 어두운 배경색
    ) {
        DeviceSelectScreen()
    }
}
