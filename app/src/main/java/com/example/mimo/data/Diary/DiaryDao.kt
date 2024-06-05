package com.example.mimo.data.Diary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Upsert
    suspend fun upsertDiary(diary: Diary)

    @Delete
    suspend fun deleteDiay(diary: Diary)

    @Query("SELECT * FROM diary ORDER BY dateAdded")
    fun getNotesOrderdByDateAdded(): Flow<List<Diary >>
}