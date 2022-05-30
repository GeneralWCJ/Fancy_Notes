package com.example.fancynotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fancynotes.data.DataSource
import com.example.fancynotes.databinding.FragmentIndividualNoteBinding
import com.example.fancynotes.model.Note

class FragmentIndividualNote : Fragment() {

    companion object {
        fun newInstance() = FragmentIndividualNote()
    }
    private var _binding: FragmentIndividualNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var note: Note
    private lateinit var viewModel: IndividualNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            val noteId = it.getInt("note_position")
            note = DataSource.notes[noteId]
            //TODO use view model instead of Datasource
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndividualNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.noteBody.setText(note.body)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[IndividualNoteViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}