package com.example.cactusnotes.notelistui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.LayoutGridItemBinding
import com.example.cactusnotes.notelistui.Notes

class NotesAdapter(var noteList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val binding =
            LayoutGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.binding.notesTitle.text = noteList[position].title
        holder.binding.notesText.text = noteList[position].note
    }

    override fun getItemCount(): Int = noteList.size

    class NotesHolder(val binding: LayoutGridItemBinding) : RecyclerView.ViewHolder(binding.root)
}