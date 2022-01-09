package com.example.notes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(
    @ColumnInfo(name = "text")
    var text: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
