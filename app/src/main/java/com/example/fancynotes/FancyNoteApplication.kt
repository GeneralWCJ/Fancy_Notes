package com.example.fancynotes

import android.app.Application
import com.example.fancynotes.data.NoteRoomDatabase

class FancyNoteApplication : Application() {
    val database: NoteRoomDatabase by lazy { NoteRoomDatabase.getDatabase(this) }
}