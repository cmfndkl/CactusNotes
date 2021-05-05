package com.example.cactusnotes.notelistui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cactusnotes.R
import com.example.cactusnotes.notelistui.adapter.NotesAdapter
import com.example.cactusnotes.databinding.ActivityNoteListBinding
import com.google.android.material.snackbar.Snackbar

class NoteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.note_list_bar)
        binding.recyclerView.adapter = NotesAdapter(noteList)
        binding.recyclerView.addItemDecoration(NotesItemDecoration())
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = gridLayoutManager

        var count = 1
        binding.floatingButton.setOnClickListener {
            when (count) {
                uiTestPosition.LOADING.ordinal -> {
                    binding.recyclerView.visibility = View.INVISIBLE
                    binding.progressIndicator.visibility = View.VISIBLE
                    count++
                }
                uiTestPosition.ONLINE.ordinal -> {
                    binding.imageView.visibility = View.VISIBLE
                    binding.notesTextView.visibility = View.VISIBLE
                    binding.imageView.setBackgroundResource(R.drawable.cactus_online)
                    binding.progressIndicator.visibility = View.INVISIBLE
                    count++
                }
                uiTestPosition.ERROR.ordinal -> {
                    binding.imageView.setBackgroundResource(R.drawable.cactus_offline)
                    binding.notesTextView.visibility = View.INVISIBLE
                    Snackbar.make(
                        binding.root,
                        getString(R.string.offline_note_error), Snackbar.LENGTH_LONG
                    ).show()
                    count++
                }
                uiTestPosition.SUCCESS.ordinal -> {
                    binding.imageView.visibility = View.INVISIBLE
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

    enum class uiTestPosition(id: Int) {
        LOADING(0),
        ONLINE(1),
        ERROR(2),
        SUCCESS(3)
    }
}
