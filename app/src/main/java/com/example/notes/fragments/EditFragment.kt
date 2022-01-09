package com.example.notes.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.notes.R
import com.example.notes.sharedpref.NotesSharedPreferences
import com.example.notes.database.Notes
import com.example.notes.databinding.FragmentEditBinding
import com.example.notes.viewmodel.NotesApplication
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditFragment : Fragment(R.layout.fragment_edit) {


    private var mBinding: FragmentEditBinding? = null
    private var noteId = 0
    private var isEmptyField = true
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((activity?.application as NotesApplication).repository)
    }
    private lateinit var notesTextSharedPref: NotesSharedPreferences
    private lateinit var noteText: String
    private var mContext: Context? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEditBinding.bind(view)
        mBinding = binding
        notesTextSharedPref = context?.let { NotesSharedPreferences(it) }!!
        noteText = notesTextSharedPref.getNote().text
        noteId = notesTextSharedPref.getNote().id
        if (noteId != 0) isEmptyField = false
        binding.noteText.setText(noteText)

        binding.bottomNavigationEdit.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.save_new_note -> insertAndUpdateNotes()
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        binding.noteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                activity?.let {
                    notesViewModel.notesListViewModel.observe(it) {
                        if (binding.noteText.text.length > 0) {
                            val text = binding.noteText.text.toString()
                            val updateNotes = Notes(text, noteId)
                            notesViewModel.update(updateNotes)
                        }
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }


    fun insertAndUpdateNotes(): Boolean {
        val text = mBinding?.noteText?.text.toString()
        val notes = Notes(text)
        if (!isEmptyField) {
            val updateNotes = Notes(text, noteId)
            notesViewModel.update(updateNotes)
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                notesViewModel.insert(notes)
                isEmptyField = false
            }
        }
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.data_container, ListFragment())?.addToBackStack(null)
            ?.commit()
        return true

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}