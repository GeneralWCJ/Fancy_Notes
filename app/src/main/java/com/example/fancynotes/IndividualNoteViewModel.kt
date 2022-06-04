package com.example.fancynotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fancynotes.data.NoteDao
import com.example.fancynotes.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class IndividualNoteViewModel(private val noteDao: NoteDao) : ViewModel() {

    fun loadNote(position : Int): Note{
        var note: Note
        viewModelScope.launch {
            note = noteDao.getNote(position)

        }
        return note
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