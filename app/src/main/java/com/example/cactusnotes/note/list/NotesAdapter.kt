package com.example.cactusnotes.note.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cactusnotes.databinding.LayoutGridItemBinding
import com.example.cactusnotes.note.Note

class NotesAdapter(var noteList: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.NotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val binding =
            LayoutGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.binding.notesTitle.text = noteList[position].title
        holder.binding.notesText.text = noteList[position].content
    }

    override fun getItemCount(): Int = noteList.size

    class NotesHolder(val binding: LayoutGridItemBinding) : RecyclerView.ViewHolder(binding.root)
}