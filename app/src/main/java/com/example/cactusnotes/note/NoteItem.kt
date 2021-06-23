package com.example.cactusnotes.note

import java.io.Serializable

data class NoteItem(
    val id: Int,
    val title: String,
    val content: String
) : Serializable
