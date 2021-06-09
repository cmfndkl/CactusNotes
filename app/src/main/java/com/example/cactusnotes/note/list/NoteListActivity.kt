package com.example.cactusnotes.note.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityNoteListBinding
import com.example.cactusnotes.login.LogInActivity
import com.example.cactusnotes.note.NoteItem
import com.example.cactusnotes.note.data.Note
import com.example.cactusnotes.note.edit.EditNoteActivity
import com.example.cactusnotes.note.edit.EditNoteActivity.Companion.INTENT_KEY_NOTE
import com.example.cactusnotes.note.edit.EditNoteActivity.Companion.REQUEST_CODE_CREATE
import com.example.cactusnotes.note.edit.EditNoteActivity.Companion.RESULT_CREATED
import com.example.cactusnotes.note.toNoteItem
import com.example.cactusnotes.service.api
import com.example.cactusnotes.userstore.UserStore
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteListBinding

    private val notesAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.note_list_bar)
        binding.recyclerView.setUp()
        fetchProducts()
        binding.floatingButton.setOnClickListener {
            val intent = Intent(this, EditNoteActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CREATE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CREATE && resultCode == RESULT_CREATED) {
            val createdNote = data!!.getSerializableExtra(INTENT_KEY_NOTE) as NoteItem
            notesAdapter.onNoteCreated(createdNote)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun RecyclerView.setUp() {
        addItemDecoration(NotesItemDecoration())
        adapter = notesAdapter
        layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
    }

    private fun fetchProducts() {
        loadingState.applyState()
        api.readAllNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                when (response.code()) {
                    200 -> onSuccess(response.body()!!)
                    401 -> tokenExpiredState {
                        navigateToLogin()
                    }.applyState()
                    else -> unexpectedErrorState.applyState()
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                connectivityProblemState {
                    fetchProducts()
                }.applyState()
            }
        })
    }

    private fun onSuccess(notes: List<Note>) {
        val noteItems = notes.map { it.toNoteItem() }
        if (noteItems.isEmpty()) {
            emptyState.applyState()
        } else {
            successState(noteItems).applyState()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.log_out -> {
            onLogoutActionClick()
            true
        }
        else -> false
    }

    private fun onLogoutActionClick() {
        UserStore(this).deleteJwt()
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }

    private fun navigateToLogin() = startActivity(Intent(this, LogInActivity::class.java))

    fun NoteListState.applyState() {
        if (notes != null) notesAdapter.submitList(notes)
        if (imageResId == null) {
            binding.imageView.isVisible = false
        } else {
            binding.imageView.setImageResource(imageResId)
            binding.imageView.isVisible = true
        }
        if (errorState != null) {
            val snackbar =
                Snackbar.make(binding.root, errorState.errorMessage, errorState.errorMessage)

            if (errorState.errorAction != null) {
                snackbar.setAction(errorState.errorAction.errorActionText) {
                    errorState.errorAction.errorAction()
                }
            }

            snackbar.show()
        }
        binding.emptyText.isVisible = emptyTextVisible
        binding.progressIndicator.isVisible = progressIndicatorVisible
    }
}
