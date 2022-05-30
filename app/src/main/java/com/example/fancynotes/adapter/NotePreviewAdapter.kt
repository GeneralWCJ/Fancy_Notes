package com.example.fancynotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fancynotes.R
import com.example.fancynotes.data.DataSource

/**
 * Inflates indivudual cards
 */
class NotePreviewAdapter(
    private val context: Context?
): RecyclerView.Adapter<NotePreviewAdapter.NoteCardViewHolder>(){
    val notes = DataSource.notes
    class NoteCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val titleText: TextView? = view?.findViewById(R.id.title_preview)
        val bodyText: TextView? = view?.findViewById(R.id.body_preview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteCardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_preview, parent, false)

        return NoteCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteCardViewHolder, position: Int) {
        val noteData = notes[position]
        holder.titleText?.text = noteData.title
        holder.bodyText?.text = noteData.body
    }
}