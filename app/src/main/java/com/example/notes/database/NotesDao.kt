package com.example.notes.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Query("SELECT*FROM notes")
    fun getAll(): Flow<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(notes: Notes)
}