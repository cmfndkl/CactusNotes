package com.example.cactusnotes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.cactusnotes.databinding.ActivityEditNoteBinding
import com.example.cactusnotes.login.data.LoginErrorResponse
import com.example.cactusnotes.login.data.LoginResponse
import com.example.cactusnotes.note.list.NoteListActivity
import com.example.cactusnotes.service.api
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.Response

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.note_list_bar)
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

    private fun noteSendRequest() {
        val notesRequest = NotesRequest(
            binding.titleEdittext.editText!!.text.toString(),
            binding.contentEdittext.editText!!.text.toString()
        )
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
}