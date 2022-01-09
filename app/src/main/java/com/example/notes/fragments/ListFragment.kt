package com.example.notes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.adapter.NotesAdapter
import com.example.notes.sharedpref.NotesPreferences
import com.example.notes.sharedpref.NotesSharedPreferences
import com.example.notes.databinding.FragmentListBinding
import com.example.notes.viewmodel.NotesApplication
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory


class ListFragment : Fragment() {

    private var mBinding: FragmentListBinding? = null
    private val binding get() = mBinding!!
    private var mAdapter = NotesAdapter()
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((activity?.application as NotesApplication).repository)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.bottomNavigationList.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.create_new_note -> {
                    val idSharedPreferences = context?.let { it1 -> NotesSharedPreferences(it1) }
                    val notesPreferences = NotesPreferences("",0)
                    idSharedPreferences?.saveNote(notesPreferences)
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.data_container, EditFragment())?.addToBackStack(null)
                        ?.commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        val swap = getSwapMan()
        swap.attachToRecyclerView(binding.rcView)
        binding.rcView.layoutManager = LinearLayoutManager(activity)
        binding.rcView.adapter = mAdapter

        activity?.let {
            notesViewModel.notesListViewModel.observe(it) {notesList->
                mAdapter.submitList(notesList)
            }
        }

        return view
    }


    private fun getSwapMan(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d("MyLog","${viewHolder.adapterPosition}")
                mAdapter.removeItem(viewHolder.adapterPosition, notesViewModel,this@ListFragment)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}