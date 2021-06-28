package com.example.cactusnotes.note

import com.example.cactusnotes.note.data.Note
import com.example.cactusnotes.note.edit.EditNoteRequest

import com.example.cactusnotes.service.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NoteRepository {
    private var noteList: List<Note>? = null

    fun fetchNotes(forceUpdate: Boolean = false, callback: NotesCallback<List<Note>>) {
        if (forceUpdate || noteList == null) {
            fetchNotesFromApi(callback)
        } else {
            callback.onSuccess(noteList!!, DataSource.CACHE)
        }
    }

    // TODO: createNote function

    fun deleteNote(noteId: Int, callback: NotesCallback<Unit>) {
        api.deleteNote(noteId).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    noteList = noteList!!.filter { it.id != noteId }
                    callback.onSuccess(Unit, DataSource.API)
                } else {
                    callback.onError(response.code())
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

    fun editNote(note: Note, callback: NotesCallback<Note>) {
        val editNoteRequest = EditNoteRequest(note.title, note.content)

        api.editNote(editNoteRequest, note.id).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    noteList = noteList!!.map { if (it.id == note.id) note else it }
                    callback.onSuccess(response.body()!!, DataSource.API)
                } else {
                    callback.onError(response.code())
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

    private fun fetchNotesFromApi(callback: NotesCallback<List<Note>>) {
        api.readAllNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                when (response.code()) {
                    200 -> {
                        noteList = response.body()!!
                        callback.onSuccess(response.body()!!, DataSource.API)
                    }
                    else -> callback.onError(response.code())
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

    interface NotesCallback<T> {
        fun onSuccess(body: T, source: DataSource)

        fun onError(responseCode: Int)

        fun onFailure()
    }

    enum class DataSource {
        API, CACHE
    }
}