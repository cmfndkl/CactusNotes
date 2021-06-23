package com.example.cactusnotes.note

import com.example.cactusnotes.note.data.Note

fun Note.toNoteItem() = NoteItem(id = id, content = content, title = title)
