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

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fancynotes.data.NoteDao
import com.example.fancynotes.data.NoteRoomDatabase
import com.example.fancynotes.model.Note
import com.example.fancynotes.model.equalsIgnoreID
import junit.framework.TestCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@RunWith(AndroidJUnit4::class)
class MoveNotesTest : TestCase("MovingNotesTesting") {

    private lateinit var viewModel: NotesListViewModel
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteRoomDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteRoomDatabase::class.java
        ).build()
        noteDao = db.itemDao()
        viewModel = NotesListViewModel(noteDao)
    }

    @Test
    fun moveNotesInMiddle() {
        try {
            runBlocking {
                // Starts off list
                val notes = mutableListOf(
                    Note(1, "Title 1", "Body 1", 0),
                    Note(2, "Title 2", "Body 2", 1),
                    Note(3, "Title 3", "Body 3", 2),
                    Note(4, "Title 4", "Body 4", 3)
                )
                // Adds notes to db
                for (note in notes) {
                    viewModel.addNote(note)
                }

                viewModel.swapNotes(1, 2)
                notes[1] = Note(3, "Title 3", "Body 3", 1)
                notes[2] = Note(2, "Title 2", "Body 2", 2)

                delay(250)
                viewModel.loadAllNotes().collect {
                    assertTrue(notes.size == it.size)
                    for (i in notes.indices) {
                        val expectedNote = notes[i]
                        val actualNote = it[i]
                        assertTrue(expectedNote.equalsIgnoreID(actualNote))
                    }
                    cancel()
                }
            }
        } catch (e: CancellationException) {
        }
    }

    @Test
    fun moveNoteToTop() {
        try {
            runBlocking {
                // Starts off list
                val notes = mutableListOf(
                    Note(5, "Title 1", "Body 1", 0),
                    Note(6, "Title 2", "Body 2", 1),
                    Note(7, "Title 3", "Body 3", 2),
                    Note(8, "Title 4", "Body 4", 3)
                )
                // Adds notes to db
                for (note in notes) {
                    viewModel.addNote(note)
                }

                viewModel.swapNotes(0, 1)
                notes[0] = Note(6, "Title 2", "Body 2", 0)
                notes[1] = Note(5, "Title 1", "Body 1", 1)

                delay(250)
                viewModel.loadAllNotes().collect {
                    assertTrue(notes.size == it.size)
                    for (i in notes.indices) {
                        val expectedNote = notes[i]
                        val actualNote = it[i]
                        assertTrue(expectedNote.equalsIgnoreID(actualNote))
                    }
                    cancel()
                }
            }
        } catch (e: CancellationException) {
        }
    }

    @Test
    fun moveNoteToBottom() {
        try {
            runBlocking {
                // Starts off list
                val notes = mutableListOf(
                    Note(9, "Title 1", "Body 1", 0),
                    Note(10, "Title 2", "Body 2", 1),
                    Note(11, "Title 3", "Body 3", 2),
                    Note(12, "Title 4", "Body 4", 3)
                )
                // Adds notes to db
                for (note in notes) {
                    viewModel.addNote(note)
                }

                viewModel.swapNotes(3, 2)
                notes[2] = Note(12, "Title 4", "Body 4", 2)
                notes[3] = Note(11, "Title 3", "Body 3", 3)

                delay(250)
                viewModel.loadAllNotes().collect {
                    assertTrue(notes.size == it.size)
                    for (i in notes.indices) {
                        val expectedNote = notes[i]
                        val actualNote = it[i]
                        assertTrue(expectedNote.equalsIgnoreID(actualNote))
                    }
                    cancel()
                }
            }
        } catch (e: CancellationException) {
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}