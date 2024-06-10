package com.example.mimo.screen.diary.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mimo.data.Diary.Diary

data class DiaryState(
    val notes: List<Diary> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val description: MutableState<String> = mutableStateOf(""),
    val dateAdded: MutableState<String> = mutableStateOf("")
) {

}