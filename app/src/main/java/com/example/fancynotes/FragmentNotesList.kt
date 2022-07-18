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

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fancynotes.adapter.NotePreviewAdapter
import com.example.fancynotes.databinding.FragmentNotesListBinding
import com.example.fancynotes.helpers.SwipeToDeleteCallBack
import com.example.fancynotes.model.Note
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FragmentNotesList : Fragment() {

    companion object {
        fun newInstance() = FragmentNotesList()
    }

    private val viewModel: NotesListViewModel by activityViewModels {
        NotesListViewModelFactory(
            (activity?.application as FancyNoteApplication).database.itemDao()
        )
    }

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val noteHolder = binding.noteHolder
        noteHolder.layoutManager = LinearLayoutManager(requireContext())
        val noteHolderAdapter = NotePreviewAdapter {
            // moves to the note editor when a note is clicked
            val action =
                FragmentNotesListDirections.actionNotesListFragmentToFragmentIndividualNote(
                    it.position
                )
            view.findNavController().navigate(action)
        }
        noteHolder.adapter = noteHolderAdapter
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallBack(viewModel, requireContext()))
        itemTouchHelper.attachToRecyclerView(noteHolder)
        lifecycle.coroutineScope.launch {
            viewModel.loadAllNotes().collect {
                noteHolderAdapter.submitList(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
        val addNoteButton = menu.findItem(R.id.action_switch_layout)
        addNoteButton.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_note)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                //Toast.makeText(context, R.string.to_be_implemented, Toast.LENGTH_SHORT).show()
                addNote()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addNote() {
        val items = binding.noteHolder.adapter!!.itemCount
        val noteToAdd = Note(null, "", "", items)
        viewModel.addNote(noteToAdd)
        // Moves to the new note based on position
        val action =
            FragmentNotesListDirections.actionNotesListFragmentToFragmentIndividualNote(items)
        requireView().findNavController().navigate(action)

    }


}