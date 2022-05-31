package com.example.fancynotes.model

/**
 * A data class representing information inside of a standard note. Includes information about
 * the title of the note, the body of the note, and the Notes Position
 */
data class Note(
    val title: String,
    val body: String,
    val position: Int
)

/**
 * Returns the first 300 characters of the [Note]. Then finishes out the word by grabbing the first
 * space after the 300 character cutoff, and adds a ... to tell user that there is more to the note
 * that has been cut off, and is visible if you select the note as a whole.
 */
fun Note.getTruncatedBody():String{
    val maxLength = 500
    return if(body.length > maxLength){
        var truncated: String = body.substring(0,maxLength)
        val left: String = body.substring(maxLength)
        truncated += left.split(" ")[0]
        truncated += " ..."
        truncated
    }
    else{
        body
    }

}