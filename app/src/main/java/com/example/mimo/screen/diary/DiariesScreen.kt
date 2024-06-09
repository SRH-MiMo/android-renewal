package com.example.mimo.screen.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.R
import com.example.mimo.component.DiaryItem
import com.example.mimo.component.TopBar
import com.example.mimo.data.Diary.Diary
import com.example.mimo.screen.diary.model.DiaryEvent
import com.example.mimo.screen.diary.model.DiaryState
import com.example.mimo.ui.theme.PurpleEnd
import com.example.mimo.ui.theme.PurpleStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    state: DiaryState,
    navController: NavController,
    onEvent: (DiaryEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(name = "하루 보고서")
        },


        floatingActionButton = {
            FloatingActionButton(onClick = {
                state.title.value = ""
                state.description.value = ""
                navController.navigate("AddNoteScreen")
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add new note")
            }
        }
    ) { paddingValues ->
        Spacer(modifier = Modifier.height(50.dp))

        LazyColumn(

            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(20.dp),
            //.background(color = Color.Black),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(state.notes.size) { index ->
                DiaryItem(
                    state = state,
                    index = index,
                    onEvent = onEvent
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
            title = "Sample Diary 1",
            description = "Description 1",
            dateAdded = "2024년 5월 39일"
        )
    )

    val sampleState = DiaryState(notes = sampleDiaries)

    DiaryScreen(
        state = sampleState,
        navController = rememberNavController(),
        onEvent = {}
    )

}
