package com.example.mimo.screen.diary.model

import com.example.mimo.data.Diary.Diary

sealed interface DiaryEvent {
    object SortDiary: DiaryEvent
    data class DeleteNote(val diary: Diary): DiaryEvent

    data class SaveNote(
        val title: String,
        val description: String,
        val dateAdded: Long,
    ): DiaryEvent
}