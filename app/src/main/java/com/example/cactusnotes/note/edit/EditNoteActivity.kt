package com.example.cactusnotes.note.edit

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityEditNoteBinding
import com.example.cactusnotes.note.NoteItem
import com.example.cactusnotes.note.NoteRepository
import com.example.cactusnotes.note.NoteRepository.NotesCallback
import com.example.cactusnotes.note.data.Note
import com.example.cactusnotes.note.toNoteItem
import com.google.android.material.snackbar.Snackbar

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding

    var noteItem: NoteItem? = null

    var isCreating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.note_list_bar)

        setUpEditTextChangeListeners()

        noteItem = intent.getSerializableExtra(INTENT_KEY_NOTE) as NoteItem?

        loadNoteItemIfNotNull()
    }

    private fun loadNoteItemIfNotNull() {
        if (noteItem != null) {
            binding.titleText.setText(noteItem!!.title)
            binding.contentText.setText(noteItem!!.content)
        }
    }

    private fun setUpEditTextChangeListeners() {
        val textChangeListener: (text: Editable?) -> Unit = {
            if (!isCreating) {
                if (noteItem == null && it.isNullOrEmpty().not()) {
                    sendCreateNoteRequest()
                } else if (noteItem != null) {
                    scheduleEditNoteRequest()
                }
            }
        }
        binding.titleText.addTextChangedListener(afterTextChanged = textChangeListener)
        binding.contentText.addTextChangedListener(afterTextChanged = textChangeListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.edit_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.share_button -> {
            true
        }
        R.id.delete_button -> {
            showDeleteDialog()
            true
        }
        else -> true
    }

    private fun scheduleEditNoteRequest() {
        val title = binding.titleText.text.toString()
        val content = binding.contentText.text.toString()

        Handler(mainLooper).postDelayed({
            if (title == binding.titleText.text.toString() && content == binding.contentText.text.toString()) {
                NoteRepository.editNote(
                    noteItem!!.id,
                    title,
                    content,
                    object : NotesCallback<Note> {
                        override fun onSuccess(body: Note, source: NoteRepository.DataSource) {
                            noteItem = body.toNoteItem()
                        }

                        override fun onError(responseCode: Int) {
                            Snackbar.make(
                                binding.root,
                                R.string.cannot_create_note,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }

                        override fun onFailure() {
                            Snackbar.make(
                                binding.root,
                                R.string.connectivity_problems_message,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }

                    })
            }
        }, 300)
    }

    private fun sendCreateNoteRequest() {
        isCreating = true

        val title = binding.titleText.text.toString()
        val content = binding.contentText.text.toString()

        NoteRepository.createNote(title, content, object : NotesCallback<Note> {
            override fun onSuccess(body: Note, source: NoteRepository.DataSource) {
                isCreating = false

                noteItem = body.toNoteItem()

                if (noteItem!!.title != binding.titleText.text.toString()
                    || noteItem!!.content != binding.contentText.text.toString()
                ) {
                    scheduleEditNoteRequest()
                }
            }

            override fun onError(responseCode: Int) {
                isCreating = false

                Snackbar.make(
                    binding.root,
                    R.string.cannot_create_note,
                    Snackbar.LENGTH_LONG
                ).show()
            }

            override fun onFailure() {
                Snackbar.make(
                    binding.root,
                    R.string.connectivity_problems_message,
                    Snackbar.LENGTH_LONG
                ).show()
            }

        })
    }

    private fun showDeleteDialog() = AlertDialog.Builder(this)
        .setMessage(R.string.alert_dialog_message)
        .setPositiveButton(R.string.delete) { _, _ ->
            deleteNote()
        }
        .setNegativeButton(R.string.cancel) { _, _ -> }
        .show()

    private fun deleteNote() {
        NoteRepository.deleteNote(noteItem!!.id, object : NotesCallback<Unit> {
            override fun onSuccess(body: Unit, source: NoteRepository.DataSource) {
                Toast.makeText(
                    applicationContext,
                    "Your note: ${noteItem!!.title} is deleted now.",
                    Toast.LENGTH_LONG
                ).show()

                finish()
            }

            override fun onError(responseCode: Int) {
                Snackbar.make(
                    binding.root,
                    R.string.error_delete,
                    Snackbar.LENGTH_LONG
                ).show()
            }

            override fun onFailure() {
                Snackbar.make(
                    binding.root,
                    R.string.connectivity_problems_message,
                    Snackbar.LENGTH_LONG
                ).show()
            }

        })
    }

    companion object {
        const val INTENT_KEY_NOTE = "note"
    }
}