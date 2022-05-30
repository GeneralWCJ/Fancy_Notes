package com.example.fancynotes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmmentIndividualNote : Fragment() {

    companion object {
        fun newInstance() = FragmmentIndividualNote()
    }

    private lateinit var viewModel: FragmmentIndividualNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmment_individual_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmmentIndividualNoteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}