package com.example.cactusnotes.note

import com.example.cactusnotes.note.data.Note
import com.example.cactusnotes.note.edit.CreateNoteRequest
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

    fun createNote(title: String, content: String, callback: NotesCallback<Note>) {
        val request = CreateNoteRequest(title, content)

        api.createNote(request).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    val note = response.body()!!
                    noteList = noteList!! + note
                    callback.onSuccess(note, DataSource.API)
                } else {
                    callback.onError(response.code())
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                callback.onFailure()
            }
        })
    }

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

    fun editNote(id: Int, title: String, content: String, callback: NotesCallback<Note>) {
        val editNoteRequest = EditNoteRequest(title, content)

        api.editNote(editNoteRequest, id).enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) {
                    noteList = noteList!!.map { if (it.id == id) Note(id, title, content) else it }
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