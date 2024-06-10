package com.example.mimo.data.Diary

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Diary(
    val title: String,
    val description: String,
    val dateAdded: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    )