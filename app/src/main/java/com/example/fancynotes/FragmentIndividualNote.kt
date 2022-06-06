package com.example.fancynotes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fancynotes.databinding.FragmentIndividualNoteBinding
import com.example.fancynotes.model.Note

class FragmentIndividualNote : Fragment() {

    companion object {
        fun newInstance() = FragmentIndividualNote()
    }
    private var _binding: FragmentIndividualNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var note: Note
    private val viewModel: IndividualNoteViewModel by activityViewModels {
        IndividualNoteViewModelFactory(
            (activity?.application as FancyNoteApplication).database.itemDao()
        )
    }
    private var _notePosition: Int? = null
    private val notePosition get()= _notePosition!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let{
            _notePosition = it.getInt("note_position")

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
        super.onViewCreated(view, savedInstanceState)
        // Retrieve the note details using the notePosition.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveItem(notePosition).observe(this.viewLifecycleOwner) { selectedNote ->
            note = selectedNote
            bind(note)
        }

    }

//    @Deprecated("Deprecated in Java")
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this)[IndividualNoteViewModel::class.java]
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.individual_note_menu,menu)
//        val editButton = menu.findItem(R.id.action_switch_layout)
//        editButton.icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_edit_title)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_switch_layout -> {
//                Toast.makeText(context, R.string.to_do, Toast.LENGTH_SHORT).show()
//                return true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun bind(note: Note) {
        binding.apply {
            noteBody.setText(note.body)
            noteTitle.setText(note.title)
        }
    }

}