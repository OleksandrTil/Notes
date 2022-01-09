package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Notes::class), version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANSE: NotesDatabase? = null
        fun getDatabase(context: Context, coroutineScope: CoroutineScope): NotesDatabase {
            return INSTANSE ?: synchronized(this) {
                val instanse = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes.db"
                ).build()
                INSTANSE = instanse
                instanse
            }
        }
    }
}