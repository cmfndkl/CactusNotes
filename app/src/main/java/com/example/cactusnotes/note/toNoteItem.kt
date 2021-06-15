package com.example.cactusnotes.note

import com.example.cactusnotes.note.data.Note

fun Note.toNoteItem() = NoteItem(content = content, title = title)
