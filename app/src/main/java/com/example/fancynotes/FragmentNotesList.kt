package com.example.fancynotes

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fancynotes.adapter.NotePreviewAdapter
import com.example.fancynotes.databinding.FragmentNotesListBinding
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
            val action =
                FragmentNotesListDirections.actionNotesListFragmentToFragmentIndividualNote(
                    it.position
                )
            view.findNavController().navigate(action)
        }
        noteHolder.adapter = noteHolderAdapter

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

    fun addNote() {
        val items = binding.noteHolder.adapter!!.itemCount
        val noteToAdd = Note(null, "", "", items)
        viewModel.addNote(noteToAdd)
        val action =
            FragmentNotesListDirections.actionNotesListFragmentToFragmentIndividualNote(items)
        requireView().findNavController().navigate(action)

    }


}