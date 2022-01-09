package com.example.notes.sharedpref

import android.content.Context
import android.util.Log


const val KEY_SHARED_PREF = "key_shared_pref"
const val KEY_NOTES_ID ="key_notes_id"
const val KEY_NOTES_TEXT ="key_notes_text"

class NotesSharedPreferences(context: Context) {

    val sharedPref = context.getSharedPreferences(KEY_SHARED_PREF, Context.MODE_PRIVATE)

    fun saveNote(notesPreferences: NotesPreferences){
        sharedPref.edit().putString(KEY_NOTES_TEXT,notesPreferences.text).apply()
        sharedPref.edit().putInt(KEY_NOTES_ID,notesPreferences.id).apply()
        Log.d("MyLog", "SaveText: ${sharedPref.getString(KEY_NOTES_TEXT,"")}",)
    }

    fun getNote(): NotesPreferences {
        Log.d("MyLog", "GetText: ${sharedPref.getString(KEY_NOTES_TEXT,"")}",)
        val text = sharedPref.getString(KEY_NOTES_TEXT,"")?:""
        val id = sharedPref.getInt(KEY_NOTES_ID,0)
        return NotesPreferences(text,id)

    }
}