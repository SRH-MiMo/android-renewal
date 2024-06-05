package com.example.mimo.data.Diary

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Diary::class],
    version = 1
)
abstract  class DiariesDatabase: RoomDatabase(){
    abstract val dao: DiaryDao
}