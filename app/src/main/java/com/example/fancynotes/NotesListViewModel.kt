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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException


class NotesListViewModel(private val noteDao: NoteDao) : ViewModel() {

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun loadAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    fun deleteNote(note: Note) {
        try {
            viewModelScope.launch {
                //deletes note to be deleted
                noteDao.delete(note)
                // gets all remaining notes
                loadAllNotes().collect {
                    //if the note isn't at the end then move positions accordingly to remove gaps
                    if (note.position != it.size) {
                        // Start at note position, reduce positions till end
                        for (i in note.position until it.size) {
                            val noteToChange = it[i]
                            noteToChange.position--
                            noteDao.update(noteToChange)
                        }
                    }
                    cancel()
                }
            }
        } catch (e: CancellationException) {
        }
    }

    fun getItem(position: Int): Note {
        var note: Note? = null
        viewModelScope.launch {
            while (note == null) {
                note = noteDao.getNote(position)
            }
        }
        return note!!
    }

    fun retrieveNote(position: Int): Note = runBlocking {
        return@runBlocking noteDao.getNote(position)
    }

    // Swaps note because onMove is called every time the position changes so it only needs a simple swap
    fun swapNotes(from: Int, to: Int) {
        viewModelScope.launch {
            var note1: Note? = noteDao.getNote(from)
            while (note1 == null) {
                note1 = noteDao.getNote(from)
            }
            var note2: Note? = noteDao.getNote(to)
            while (note2 == null) {
                note2 = noteDao.getNote(to)
            }
            note1.position = to
            noteDao.update(note1)
            note2.position = from
            noteDao.update(note2)
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