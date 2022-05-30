package com.example.fancynotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fancynotes.adapter.NotePreviewAdapter
import com.example.fancynotes.databinding.FragmentNotesListBinding

class FragmentNotesList : Fragment() {

    companion object {
        fun newInstance() = FragmentNotesList()
    }

    private lateinit var viewModel: NotesListViewModel
    private var _binding: FragmentNotesListBinding? = null
    val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val noteHolder = binding.noteHolder
        noteHolder.layoutManager = LinearLayoutManager(requireContext())
        noteHolder.adapter = NotePreviewAdapter(requireContext())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[NotesListViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}