package com.example.fancynotes.model

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A data class representing information inside of a standard note. Includes information about
 * the title of the note, the body of the note, and the Notes Position
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull @ColumnInfo(name = "note_title")
    val title: String,
    @NonNull @ColumnInfo(name = "note_body")
    val body: String,
    @NonNull @ColumnInfo(name = "note_position")
    val position: Int
)

/**
 * Returns the first 300 characters of the [Note]. Then finishes out the word by grabbing the first
 * space after the 300 character cutoff, and adds a ... to tell user that there is more to the note
 * that has been cut off, and is visible if you select the note as a whole.
 */
fun Note.getTruncatedBody(): String {
    val maxLength = 500
    return if (body.length > maxLength) {
        var truncated: String = body.substring(0, maxLength)
        val left: String = body.substring(maxLength)
        truncated += left.split(" ")[0]
        truncated += " ..."
        truncated
    } else {
        body
    }

}