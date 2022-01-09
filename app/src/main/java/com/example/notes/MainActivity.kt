package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.adapter.NotesAdapter
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.fragments.ListFragment
import com.example.notes.viewmodel.NotesApplication
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.data_container, ListFragment())
                .addToBackStack(null).commit()
        }
    }

}