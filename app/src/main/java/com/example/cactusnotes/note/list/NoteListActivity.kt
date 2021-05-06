package com.example.cactusnotes.note.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityNoteListBinding
import com.example.cactusnotes.login.LogInActivity
import com.example.cactusnotes.note.list.NoteListActivity.UiState.*
import com.example.cactusnotes.userstore.UserStore
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

        var uiState = LOADING

        binding.floatingButton.setOnClickListener {
            when (uiState) {
                LOADING -> {
                    binding.recyclerView.visibility = View.INVISIBLE
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                ONLINE -> {
                    binding.imageView.visibility = View.VISIBLE
                    binding.notesTextView.visibility = View.VISIBLE
                    binding.imageView.setBackgroundResource(R.drawable.cactus_online)
                    binding.progressIndicator.visibility = View.INVISIBLE
                }
                ERROR -> {
                    binding.imageView.setBackgroundResource(R.drawable.cactus_offline)
                    binding.notesTextView.visibility = View.INVISIBLE
                    Snackbar.make(
                        binding.root,
                        getString(R.string.offline_note_error), Snackbar.LENGTH_LONG
                    ).show()
                }
                SUCCESS -> {
                    binding.imageView.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                }
            }

            uiState = uiState.next()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)


        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val userStore = UserStore(this)
        return when (item.itemId) {
            R.id.log_out -> {
                userStore.deleteJwt()
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
                true
            }
            else -> false
        }
    }

    enum class UiState {
        LOADING,
        ONLINE,
        ERROR,
        SUCCESS
    }

    private fun UiState.next() = when (this) {
        LOADING -> ONLINE
        ONLINE -> ERROR
        ERROR -> SUCCESS
        SUCCESS -> LOADING
    }
}
