package com.example.cactusnotes.notelistui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cactusnotes.R
import com.example.cactusnotes.notelistui.Notes

class NotesAdapter(var noteList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_grid_item, parent, false)
        return NotesHolder(view)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.noteTitle.text = noteList[position].title
        holder.noteText.text = noteList[position].note
    }

    override fun getItemCount(): Int = noteList.size

    class NotesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById<TextView>(R.id.notesTitle)
        val noteText: TextView = itemView.findViewById<TextView>(R.id.notesText)
    }
}