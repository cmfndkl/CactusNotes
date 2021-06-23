package com.example.cactusnotes.note.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cactusnotes.databinding.LayoutGridItemBinding
import com.example.cactusnotes.note.NoteItem

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesHolder>() {
    private var noteList: MutableList<NoteItem> = mutableListOf()

    var itemClickListener: (NoteItem) -> Unit = {}

    fun submitList(notes: List<NoteItem>) {
        noteList = notes.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val binding =
            LayoutGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.binding.notesTitle.text = noteList[position].title
        holder.binding.notesText.text = noteList[position].content
        holder.binding.root.setOnClickListener {
            itemClickListener(noteList[position])
        }
    }

    override fun getItemCount(): Int = noteList.size

    fun onNoteCreated(createdNote: NoteItem) {
        noteList.add(0, createdNote)
        notifyDataSetChanged()
    }

    fun onNoteEdited(note: NoteItem) {
        val index = noteList.indexOfFirst { it.id == note.id }
        noteList.removeAt(index)
        noteList.add(0, note)
        notifyItemRangeChanged(0, index + 1)
    }

    fun containsExactly(note: NoteItem) = noteList.contains(note)

    fun containsNote(note: NoteItem) = noteList.any { it.id == note.id }

    class NotesHolder(val binding: LayoutGridItemBinding) : RecyclerView.ViewHolder(binding.root)
}