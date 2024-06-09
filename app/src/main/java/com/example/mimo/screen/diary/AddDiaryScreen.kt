package com.example.mimo.screen.diary

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.data.Diary.Diary
import com.example.mimo.screen.diary.model.DiaryEvent
import com.example.mimo.screen.diary.model.DiaryState
import com.example.mimo.supabase
import com.example.mimo.ui.theme.PurpleEnd
import com.example.mimo.ui.theme.PurpleStart
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
val localDate: LocalDate = LocalDate.now()

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddDiaryScreen(
    state: DiaryState,
    navController: NavController,
    onEvent: (DiaryEvent) -> Unit
) {

    Surface{
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                        contentDescription = "Back Button",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "하루 보고서",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(40.dp)) // Ensure space for IconButton
            }




            Spacer(modifier = Modifier.height(20.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "오늘의 날짜",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = com.example.mimo.screen.diary.model.localDate.format(
                        DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
                    ),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.title.value,
                onValueChange = {
                    state.title.value = it
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                placeholder = {
                    Text(text = "보고서 제목을 입력해주세요")
                }

            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(16.dp),
                value = state.description.value,
                onValueChange = {
                    state.description.value = it
                },
                placeholder = {
                    Text(text = "보고서 내용을 입력해주세요")
                }
            )

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
                        onEvent(
                            DiaryEvent.SaveNote(
                                title = state.title.value,
                                description = state.description.value,
                                dateAdded = localDate.toString()
                            ))
                        navController.popBackStack()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "저장하기",
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    val sampleDiaries = listOf(
        Diary(
            title = "Sample Diary 1",
            description = "Description 1",
            dateAdded = "2024년 5월 39일"
        )
    )

    val sampleState = DiaryState(notes = sampleDiaries)

    AddDiaryScreen(
        state = sampleState,
        navController = rememberNavController(),
        onEvent = {}
    )

}













