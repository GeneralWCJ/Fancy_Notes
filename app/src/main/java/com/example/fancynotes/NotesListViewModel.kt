package com.example.fancynotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fancynotes.data.NoteDao
import com.example.fancynotes.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class NotesListViewModel(private val noteDao:NoteDao) : ViewModel() {
    // TODO: Implement the ViewModel

    fun addNote(note: Note){
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun loadAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }
}

class NotesListViewModelFactory(private val noteDao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesListViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}