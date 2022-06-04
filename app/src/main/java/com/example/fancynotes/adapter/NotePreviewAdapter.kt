package com.example.fancynotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fancynotes.databinding.NotePreviewBinding
import com.example.fancynotes.model.Note
import com.example.fancynotes.model.getTruncatedBody

/**
 * Inflates individual cards
 */

class NotePreviewAdapter(
    private val onItemClicked: (Note) -> Unit
) : ListAdapter<Note, NotePreviewAdapter.NoteViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {

                return oldItem.id == newItem.id
                //TODO("Use Primary keys once room is implemented")
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(
            NotePreviewBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoteViewHolder(
        private var binding: NotePreviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.bodyPreview.text = note.getTruncatedBody()
            binding.titlePreview.text = note.title
        }
    }
}