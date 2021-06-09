package com.example.cactusnotes.note.edit

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityEditNoteBinding
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

    var state = NoteState.CREATING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.note_list_bar)

        setUpEditTextChangeListeners()
    }

    private fun setUpEditTextChangeListeners() {
        val textChangeListener: (text: Editable?) -> Unit = {
            if (state == NoteState.CREATING && it.isNullOrEmpty().not()) {
                sendCreateNoteRequest()
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

    private fun sendCreateNoteRequest() {
        state = NoteState.IN_PROGRESS

        val request = CreateNoteRequest(
            binding.titleText.text.toString(),
            binding.contentText.text.toString()
        )

        api.createNote(request).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    state = NoteState.EDITING

                    val data = Intent()
                    data.putExtra(INTENT_KEY_NOTE, response.body()!!.toNoteItem())
                    setResult(RESULT_CREATED, data)
                } else {
                    Snackbar.make(
                        binding.root,
                        R.string.cannot_create_note,
                        Snackbar.LENGTH_LONG
                    ).show()

                    state = NoteState.CREATING
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Snackbar.make(
                    binding.root,
                    R.string.connectivity_problems_message,
                    Snackbar.LENGTH_LONG
                ).show()

                state = NoteState.CREATING
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

    enum class NoteState {
        CREATING,
        EDITING,
        IN_PROGRESS
    }

    companion object {
        const val REQUEST_CODE_CREATE = 1001
        const val RESULT_CREATED = 9001
        const val INTENT_KEY_NOTE = "note"
    }
}