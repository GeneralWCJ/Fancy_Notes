package com.example.fancynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fancynotes.adapter.NotePreviewAdapter
import com.example.fancynotes.databinding.FragmentNotesListBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val noteHolder = binding.noteHolder
        noteHolder.layoutManager = LinearLayoutManager(requireContext())
        val noteHolderAdapter = NotePreviewAdapter {
            val action =
                FragmentNotesListDirections.actionNotesListFragmentToFragmentIndividualNote(
                    it.position,
                    it.title
                )
            view.findNavController().navigate(action)
        }
        noteHolder.adapter = noteHolderAdapter
        //noteHolderAdapter.submitList(DataSource.notes)

        //Load stuff

//        GlobalScope.launch(Dispatchers.IO) {
//            noteHolderAdapter.submitList(viewModel.loadAllNotes())
//        }

        lifecycle.coroutineScope.launch {
            viewModel.loadAllNotes().collect {
                noteHolderAdapter.submitList(it)
            }
        }

    }

//    @Deprecated("Deprecated in Java")
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this)[NotesListViewModel::class.java]
//        // TODO: Use the ViewModel
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}