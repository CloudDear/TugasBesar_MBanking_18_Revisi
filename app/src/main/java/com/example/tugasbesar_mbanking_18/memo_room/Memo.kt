package com.example.tugasbesar_mbanking_18.memo_room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val memo: String
)