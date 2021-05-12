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
import com.example.cactusnotes.note.NoteItem
import com.example.cactusnotes.note.data.Note
import com.example.cactusnotes.service.api
import com.example.cactusnotes.userstore.UserStore
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.note_list_bar)
        binding.recyclerView.addItemDecoration(NotesItemDecoration())
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = gridLayoutManager
        updateUI(UiState.LOADING)
        fetchProducts()
    }

    private fun fetchProducts() {
        api.readAllNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                when (response.code()) {
                    200 -> onSuccess(response.body()!!)
                    401 -> onTokenExpired()
                    else -> onUnexpectedError()
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                updateUI(UiState.ERROR)
                Snackbar.make(
                    binding.root, R.string.offline_note_error,
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        })
    }

    private fun onUnexpectedError() {
        Snackbar.make(
            binding.root, R.string.unexpected_error_occurred,
            BaseTransientBottomBar.LENGTH_LONG
        ).show()
    }

    private fun onTokenExpired() {
        Snackbar.make(
            binding.root, R.string.your_session_is_expired,
            Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.log_in) {
            navigateToLogin()
        }.show()
    }

    private fun onSuccess(notes: List<Note>) {
        val noteItems = notes.map {
            NoteItem(content = it.content, title = it.title)
        }
        binding.recyclerView.adapter = NotesAdapter(noteItems)
        if (noteItems.count() <= 0) {
            updateUI(UiState.ONLINE)
        } else updateUI(UiState.SUCCESS)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)
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

    private fun navigateToLogin() = startActivity(Intent(this, LogInActivity::class.java))

    private fun updateUI(uiState: UiState) {
        when (uiState) {
            UiState.LOADING -> {
                binding.recyclerView.visibility = View.INVISIBLE
                binding.progressIndicator.visibility = View.VISIBLE
            }
            UiState.ONLINE -> {
                binding.imageView.visibility = View.VISIBLE
                binding.notesTextView.visibility = View.VISIBLE
                binding.imageView.setBackgroundResource(R.drawable.cactus_online)
                binding.progressIndicator.visibility = View.INVISIBLE
            }
            UiState.ERROR -> {
                binding.imageView.setBackgroundResource(R.drawable.cactus_offline)
                binding.notesTextView.visibility = View.INVISIBLE
                binding.imageView.visibility = View.VISIBLE
                binding.progressIndicator.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.INVISIBLE
                Snackbar.make(
                    binding.root,
                    getString(R.string.offline_note_error), Snackbar.LENGTH_LONG
                ).show()
            }
            UiState.SUCCESS -> {
                binding.imageView.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressIndicator.visibility = View.INVISIBLE
            }
        }
    }
}
