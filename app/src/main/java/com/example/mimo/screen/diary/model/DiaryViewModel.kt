package com.example.mimo.screen.diary.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mimo.data.Diary.Diary
import com.example.mimo.data.Diary.DiaryDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiaryViewModel(
    private val dao: DiaryDao
): ViewModel() {
    private val isSortedByDateAdded = MutableStateFlow(true)

    private var notes =
        isSortedByDateAdded.flatMapLatest {sort ->
            if (sort) {
                dao.getNotesOrderdByDateAdded()
            } else {
                dao.getNotesOrderdByDateAdded()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(DiaryState())
    val state =
        combine((_state), isSortedByDateAdded, notes) { state, isSortedByDateAdded, notes ->
            state.copy(
                notes = notes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DiaryState())
    fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteDiay(event.diary)
                }
            }
            is DiaryEvent.SaveNote -> {
                val diary = Diary(
                    title = state.value.title.value,
                    description = state.value.description.value,
                    dateAdded = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertDiary(diary)
                }

                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        description = mutableStateOf("")
                    )
                }

            }
            DiaryEvent.SortDiary -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
        }
    }
}