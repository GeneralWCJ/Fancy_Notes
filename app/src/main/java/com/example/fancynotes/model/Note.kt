package com.example.fancynotes.model

/**
 * A data class representing information inside of a standard note
 */
data class Note(
    val title: String,
    val body: String,
    val position: Int
)