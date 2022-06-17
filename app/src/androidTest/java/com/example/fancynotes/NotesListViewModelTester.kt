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
class NotesListViewModelTester : TestCase("ViewModelTesting") {

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
    fun addAndLoadNote() {
        try {
            runBlocking {
                val notes: List<Note> = listOf(
                    Note(4, "Title 2", "Body 2", 1),
                    Note(3, "Title 1", "Body 1", 0)
                )

                for (note in notes) {
                    viewModel.addNote(note)
                }
                delay(500)
                viewModel.loadAllNotes().collect {
                    assertTrue(notes.size == it.size)
                    for (i in notes.indices) {
                        val expectednote = notes[i]
                        val actualnote = it[i]
                        assertTrue(expectednote.equalsIgnoreID(actualnote))
                    }
                    cancel()
                }
            }
        } catch (e: CancellationException) {
        }
    }

    @Test
    fun deleteFirstNote() {
        try {
            runBlocking {
                // Starts off list
                val notes = mutableListOf<Note>(
                    Note(2, "Title 3", "Body 3", 2),
                    Note(1, "Title 2", "Body 2", 1),
                    Note(0, "Title 1", "Body 1", 0)
                )
                // Adds notes to db
                for (note in notes) {
                    viewModel.addNote(note)
                }

                val notetoremove = notes[2]
                viewModel.deleteNote(notetoremove)
                notes.remove(notetoremove)

                notes[1] = Note(1, "Title 2", "Body 2", 0)
                notes[0] = Note(2, "Title 3", "Body 3", 1)

                delay(500)
                viewModel.loadAllNotes().collect {
                    assertTrue(notes.size == it.size)
                    for (i in notes.indices) {
                        val expectednote = notes[i]
                        val actualnote = it[i]
                        assertTrue(expectednote.equalsIgnoreID(actualnote))
                    }
                    cancel()
                }
            }
        } catch (e: CancellationException) {
        }
    }

    @Test
    fun deleteLastNote() {
        try {
            runBlocking {
                // Starts off list
                val notes = mutableListOf<Note>(
                    Note(5, "Title 3", "Body 3", 2),
                    Note(6, "Title 2", "Body 2", 1),
                    Note(7, "Title 1", "Body 1", 0)
                )
                // Adds notes to db
                for (note in notes) {
                    viewModel.addNote(note)
                }

                val notetoremove = notes[0]
                viewModel.deleteNote(notetoremove)
                notes.remove(notetoremove)

                notes[0] = Note(6, "Title 2", "Body 2", 1)
                notes[1] = Note(7, "Title 1", "Body 1", 0)

                delay(500)
                viewModel.loadAllNotes().collect {
                    assertTrue(notes.size == it.size)
                    for (i in notes.indices) {
                        val expectednote = notes[i]
                        val actualnote = it[i]
                        assertTrue(expectednote.equalsIgnoreID(actualnote))
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