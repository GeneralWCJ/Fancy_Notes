package com.example.fancynotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fancynotes.data.NoteDao
import com.example.fancynotes.model.Note
import kotlinx.coroutines.launch


class IndividualNoteViewModel(private val noteDao: NoteDao) : ViewModel() {

//    fun loadNote(position : Int): Note{
//        var note: Note
//
//        return note
//    }

    /**
     * Returns true if both title and body are not empty
     */
    fun isNoteValid(noteTitle: String, noteBody: String): Boolean {
        if (noteTitle.isBlank() || noteBody.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Retrieve an [Note] from the repository.
     */
    suspend fun retrieveItem(position: Int): Note {
        return noteDao.getNote(position)
    }

    fun editNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
        }

    }

}

class IndividualNoteViewModelFactory(private val noteDao: NoteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IndividualNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IndividualNoteViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}