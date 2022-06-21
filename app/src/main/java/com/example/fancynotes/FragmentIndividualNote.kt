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

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.fancynotes.databinding.FragmentIndividualNoteBinding
import com.example.fancynotes.model.Note
import kotlinx.coroutines.launch

class FragmentIndividualNote : Fragment() {

    companion object {
        fun newInstance() = FragmentIndividualNote()
    }

    private var _binding: FragmentIndividualNoteBinding? = null
    private val binding get() = _binding!!
    private var _note: Note? = null
    private val note get() = _note!!
    private val viewModel: IndividualNoteViewModel by activityViewModels {
        IndividualNoteViewModelFactory(
            (activity?.application as FancyNoteApplication).database.itemDao()
        )
    }
    private var _notePosition: Int? = null
    private val notePosition get() = _notePosition!!

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
        // Retrieves the note details using the notePosition.
        // then takes the data and places it on screen
        lifecycleScope.launch {
            _note = viewModel.retrieveItem(notePosition)
            bind(note)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

    private fun bind(note: Note) {
        binding.apply {
            noteBody.setText(note.body)
            noteBody.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    onBodyTextChanged(s)
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                }
            })
            noteTitle.setText(note.title)
            noteTitle.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    onTitleTextChanged(s)
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                }
            })
        }
    }

    fun onBodyTextChanged(s: CharSequence) {
        _note!!.body = s.toString()
        viewModel.editNote(note)
    }

    fun onTitleTextChanged(s: CharSequence) {
        _note!!.title = s.toString()
        viewModel.editNote(note)
    }

}