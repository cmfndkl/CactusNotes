package com.example.cactusnotes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.ActivityEditNoteBinding
import com.example.cactusnotes.note.list.NoteListActivity

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