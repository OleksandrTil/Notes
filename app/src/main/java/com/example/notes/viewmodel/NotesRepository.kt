package com.example.notes.viewmodel

import androidx.annotation.WorkerThread
import com.example.notes.database.Notes
import com.example.notes.database.NotesDao
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NotesDao) {

    var notesList: Flow<List<Notes>> = notesDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(notes: Notes) {
        notesDao.insert(notes)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(notes: Notes) {
        notesDao.update(notes)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun delete(notes: Notes) {
        notesDao.delete(notes)
    }

}