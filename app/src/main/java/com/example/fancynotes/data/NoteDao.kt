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

package com.example.fancynotes.data

import androidx.room.*
import com.example.fancynotes.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    /**
     * Gets all of the [Note] in the database, uses a [Flow] so it must be collected, but is
     * automatically updated.
     */
    @Query("SELECT * FROM notes ORDER BY note_position ASC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    /**
     * Updates a [Note] with a changed note. Bases it off of the primary keys, so don't expect to
     * change the primary keys because behavior will be unexpected
     */
    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE note_position == :position")
    suspend fun getNote(position: Int): Note

    /**
     * Gets all of the [Note] in the database. Returns a snapshot of the list that does not change
     */
    @Query("SELECT * FROM notes ORDER BY note_position ASC")
    suspend fun getAllNotesSnapshot(): List<Note>

}