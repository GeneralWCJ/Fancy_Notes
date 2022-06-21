/*
 * Copyright (c) 2022 Joseph Wilson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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