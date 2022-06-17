package com.example.fancynotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fancynotes.data.NoteDao
import com.example.fancynotes.model.Note
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NotesListViewModel(private val noteDao:NoteDao) : ViewModel() {

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun loadAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            //deletes note to be deleted
            noteDao.delete(note)
            // gets all remaining notes
            loadAllNotes().collect {
                //if the note isnt at the end then move positons accordingly to remove gaps
                if (note.position != it.size - 1) {
                    // Start at note posotion, reduce posotions till end
                    for (i in note.position until it.size) {
                        it[i].position--
                    }
                }
                cancel()
            }
        }
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