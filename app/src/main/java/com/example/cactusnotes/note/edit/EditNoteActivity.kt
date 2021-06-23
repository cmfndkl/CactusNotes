package com.example.cactusnotes.note.edit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityEditNoteBinding
import com.example.cactusnotes.note.NoteItem
import com.example.cactusnotes.note.data.Note
import com.example.cactusnotes.note.list.NoteListActivity
import com.example.cactusnotes.note.toNoteItem
import com.example.cactusnotes.service.api
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding

    var noteItem: NoteItem? = null
        set(value) {
            setAsActivityResult(value)

            field = value
        }

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
            deleteDialog()
            true
        }
        else -> true
    }

    private fun scheduleEditNoteRequest() {
        val request = EditNoteRequest(
            binding.titleText.text.toString(),
            binding.contentText.text.toString()
        )

        Log.e("EditNoteActivity", "New edit scheduled:\n${request.title}\n${request.content}")

        Handler(mainLooper).postDelayed({
            if (request.title == binding.titleText.text.toString() && request.content == binding.contentText.text.toString()) {
                api.editNote(request, noteItem!!.id).enqueue(object : Callback<Note> {
                    override fun onResponse(call: Call<Note>, response: Response<Note>) {
                        if (response.isSuccessful) {
                            Log.e("EditNoteActivity", "Edit operation successful")
                            noteItem = response.body()!!.toNoteItem()
                        } else {
                            Snackbar.make(
                                binding.root,
                                R.string.cannot_create_note,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Note>, t: Throwable) {
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

    private fun setAsActivityResult(value: NoteItem?) {
        val data = Intent()
        data.putExtra(INTENT_KEY_NOTE, value)
        setResult(RESULT_NOTE, data)
    }

    private fun sendCreateNoteRequest() {
        isCreating = true

        val request = CreateNoteRequest(
            binding.titleText.text.toString(),
            binding.contentText.text.toString()
        )

        Log.e("EditNoteActivity", "New note create request:\n${request.title}\n${request.content}")

        api.createNote(request).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    Log.e("EditNoteActivity", "Create operation successful")
                    noteItem = response.body()!!.toNoteItem()

                    if (noteItem!!.title != binding.titleText.text.toString()
                        || noteItem!!.content != binding.contentText.text.toString()
                    ) {
                        scheduleEditNoteRequest()
                    }

                } else {
                    Snackbar.make(
                        binding.root,
                        R.string.cannot_create_note,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                isCreating = false
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Snackbar.make(
                    binding.root,
                    R.string.connectivity_problems_message,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun deleteDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(R.string.alert_dialog_message)
        alert.setPositiveButton(R.string.delete) { dialog, which ->
            startActivity(Intent(this, NoteListActivity::class.java))
            finish()
        }
        alert.setNegativeButton(R.string.cancel) { dialog, which -> false }
        alert.show()
    }

    companion object {
        const val RESULT_NOTE = 9001
        const val INTENT_KEY_NOTE = "note"
    }
}