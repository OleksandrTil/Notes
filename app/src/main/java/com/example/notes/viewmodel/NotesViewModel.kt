package com.example.notes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.notes.database.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    val notesListViewModel = repository.notesList.asLiveData()
    val noteId = MutableLiveData<Int>()

    init {
        noteId.value = 0
    }

    fun insert(notes: Notes) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insert(notes)
        }
    }

    fun update(notes: Notes) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.update(notes)
        }
    }

    fun delete(notes: Notes) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.delete(notes)
        }
    }
}