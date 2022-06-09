package com.example.fancynotes.model

import androidx.annotation.NonNull
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
    val id: Int? = 0,
    @NonNull @ColumnInfo(name = "note_title")
    var title: String,
    @NonNull @ColumnInfo(name = "note_body")
    var body: String,
    @NonNull @ColumnInfo(name = "note_position")
    val position: Int
)

/**
 * Returns the first 500 characters of the [Note]. Then finishes out the word by grabbing the first
 * space, comma, question mark, period, exclamation mark, or backslash after the 300 character
 * cutoff, and adds a ... to tell user that there is more to the note that has been cut off, and is
 * visible if you select the note as a whole.
 */
fun Note.getTruncatedBody(): String {
    val maxLength = 500
    return if (body.length > maxLength) {
        var truncated: String = body.substring(0, maxLength)
        val left: String = body.substring(maxLength)
        truncated += left.split(Regex("[\\s,?.!\\\\]"))[0]
        truncated += " ..."
        truncated
    } else {
        body
    }
}


/**
 * Because room changes the null to a value because autogenerate is turned on, this lets you test
 * if two notes have the same content excluding the ID.
 */
fun Note.equalsIgnoreID(note: Note): Boolean {
    if ((body == note.body) && (title == note.title) && (position == note.position)) {
        return true
    }
    return false
}