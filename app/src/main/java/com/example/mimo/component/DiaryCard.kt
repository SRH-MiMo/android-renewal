package com.example.mimo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource // Import painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mimo.R
import com.example.mimo.data.Diary.Diary
import com.example.mimo.screen.diary.model.DiaryEvent
import com.example.mimo.screen.diary.model.DiaryState

object Variables {
    val colorBackLightGray: Color = Color(0xFF212121)
    val moreLight: Color = Color(0x80FFFFFF)
}

@Composable
fun DiaryItem(
    state: DiaryState,
    index: Int,
    onEvent: (DiaryEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Variables.colorBackLightGray)
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.width(5.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = state.notes[index].title,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = state.notes[index].description,
                fontSize = 15.sp,
                color = Variables.moreLight
            )

        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = state.notes[index].dateAdded,
                fontSize = 15.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
                modifier = Modifier.padding(end = 12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            IconButton(
                onClick = {
                    onEvent(DiaryEvent.DeleteNote(state.notes[index])) // Assuming you will handle delete logic
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tttrasssh), // Correct usage with painterResource
                    contentDescription = "Delete Note",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun DiaryScreenPreview() {
    val sampleDiaries = listOf(
        Diary(
            title = "일기 제목",
            description = "설?명",
            dateAdded = "2024년 5월 25일"
        ),
    )

    val sampleState = DiaryState(notes = sampleDiaries)

    DiaryItem(
        state = sampleState,
        index = 0,
        onEvent = {}
    )
}
