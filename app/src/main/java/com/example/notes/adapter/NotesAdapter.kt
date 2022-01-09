package com.example.notes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.MainActivity
import com.example.notes.R
import com.example.notes.sharedpref.NotesSharedPreferences
import com.example.notes.database.Notes
import com.example.notes.sharedpref.NotesPreferences
import com.example.notes.databinding.LayoutItemBinding
import com.example.notes.fragments.EditFragment
import com.example.notes.viewmodel.NotesViewModel


class NotesAdapter() :
    ListAdapter<Notes, NotesAdapter.NotesViewHolder>(NotesComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentList = getItem(position)
        if (currentList != null) {
            holder.bind(currentList.text)
            holder.binding.notesItem.setOnClickListener {
                val activity = it.context as MainActivity
                val notesSharedPreferences = NotesSharedPreferences(it.context)
                val notesPreferences = NotesPreferences(currentList.text, currentList.id)
                notesSharedPreferences.saveNote(notesPreferences)

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.data_container, EditFragment())
                    .addToBackStack(null)
                    .commit()


            }
        }
    }

    fun removeItem(position: Int, db: NotesViewModel, fragment: Fragment) {
        val list = currentList
        db.notesListViewModel.observe(fragment) {
            db.delete(list.get(position))
            Log.d("MyLog", "${list.get(position)}")
            notifyItemRangeChanged(0, list.size)
            notifyItemRemoved(position)
        }
    }

    class NotesViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val itemLength = 45
        fun bind(text: String) {
            binding.notesItem.layoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
            if (text.length > itemLength) {
                binding.notesItem.text = "${text.substring(0, itemLength)} ..."
            }
             else binding.notesItem.text = text
        }

        companion object {
            fun create(parent: ViewGroup): NotesViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = LayoutItemBinding.inflate(inflater)
                val params = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                view.root.layoutParams = params
                return NotesViewHolder(view)
            }
        }
    }

    class NotesComparator : DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }

    }

}