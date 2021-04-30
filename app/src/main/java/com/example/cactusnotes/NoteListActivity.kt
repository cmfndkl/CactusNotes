package com.example.cactusnotes

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cactusnotes.adapter.StaggeredRecyclerViewAdapter
import com.example.cactusnotes.databinding.ActivityNoteListBinding
import com.google.android.material.snackbar.Snackbar


class NoteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.note_list_bar)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = StaggeredRecyclerViewAdapter(noteList)
        binding.recyclerView.addItemDecoration(NotesItemDecoration())
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = gridLayoutManager

        var count = 1

        binding.floatingButton.setOnClickListener {
            when (count) {
                0 -> {
                    binding.recyclerView.visibility = View.INVISIBLE
                    binding.progressIndicator.visibility = View.VISIBLE
                    count++
                }
                1 -> {
                    binding.shapeableImageView.visibility = View.VISIBLE
                    binding.notesTextView.visibility = View.VISIBLE
                    binding.shapeableImageView.setBackgroundResource(R.drawable.cactusonline)
                    binding.progressIndicator.visibility = View.INVISIBLE
                    count++
                }
                2 -> {
                    binding.shapeableImageView.setBackgroundResource(R.drawable.cactusoffline)
                    binding.notesTextView.visibility = View.INVISIBLE
                    Snackbar.make(
                        binding.root,
                        getString(R.string.offline_note_error), Snackbar.LENGTH_LONG
                    ).show()
                    count++
                }
                3 -> {
                    binding.shapeableImageView.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    count = 0
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}