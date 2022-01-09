package com.example.notes.viewmodel

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.notes.database.NotesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication: Application() {
val scope = CoroutineScope(SupervisorJob())
val database by lazy { NotesDatabase.getDatabase(applicationContext,scope) }
    val repository by lazy { NotesRepository(database.notesDao()) }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
MultiDex.install(this)
    }

}