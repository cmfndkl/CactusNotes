package com.example.cactusnotes.note.list

import com.example.cactusnotes.note.NoteItem

data class NoteListState(
    val notes: List<NoteItem>? = null,
    val imageResId: Int? = null,
    val errorState: ErrorState? = null,
    val emptyTextVisible: Boolean = false,
    val progressIndicatorVisible: Boolean = false,
)

data class ErrorState(
    val errorMessage: Int,
    val errorDuration: Int,
    val errorAction: ErrorAction?
)

data class ErrorAction(
    val errorActionText: Int,
    val errorAction: () -> Unit,
)
