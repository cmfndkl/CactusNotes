package com.example.cactusnotes.note.list

import com.example.cactusnotes.R
import com.example.cactusnotes.note.NoteItem
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE

val loadingState = NoteListState().copy(
    progressIndicatorVisible = true
)

val emptyState = NoteListState(
    notes = emptyList(),
    imageResId = R.drawable.cactus_online
)

fun successState(notes: List<NoteItem>) = NoteListState().copy(
    notes = notes
)

fun tokenExpiredState(navigateToLogin: () -> Unit) = NoteListState().copy(
    errorState = ErrorState(
        errorMessage = R.string.your_session_is_expired,
        errorDuration = LENGTH_INDEFINITE,
        errorAction = ErrorAction(
            errorActionText = R.string.log_in,
            errorAction = navigateToLogin
        )
    )
)

val unexpectedErrorState = NoteListState(
    imageResId = R.drawable.cactus_offline,
    errorState = ErrorState(
        errorMessage = R.string.unexpected_error_occurred,
        errorDuration = LENGTH_INDEFINITE,
        errorAction = null
    )
)

fun connectivityProblemState(fetchProducts: () -> Unit) = NoteListState(
    imageResId = R.drawable.cactus_offline,
    errorState = ErrorState(
        errorMessage = R.string.offline_note_error,
        errorDuration = LENGTH_INDEFINITE,
        errorAction = ErrorAction(
            errorActionText = R.string.retry,
            errorAction = fetchProducts
        )
    )
)