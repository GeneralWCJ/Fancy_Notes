package com.example.fancynotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.individual_note_menu,menu)
        val editButton = menu.findItem(R.id.action_switch_layout)
        editButton.icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_edit_title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                Toast.makeText(context, R.string.todo, Toast.LENGTH_SHORT).show()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}