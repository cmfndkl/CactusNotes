package com.example.cactusnotes.service

import com.example.cactusnotes.login.data.LoginRequest
import com.example.cactusnotes.login.data.LoginResponse
import com.example.cactusnotes.note.data.Note
import com.example.cactusnotes.note.edit.CreateNoteRequest
import com.example.cactusnotes.note.edit.EditNoteRequest
import com.example.cactusnotes.signup.data.RegisterRequest
import com.example.cactusnotes.signup.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface CactusApi {
    @POST("/auth/local/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/auth/local")
    fun login(@Body requestLogin: LoginRequest): Call<LoginResponse>

    @GET("/notes")
    fun readAllNotes(): Call<List<Note>>

    @POST("/notes")
    fun createNote(@Body requestNote: CreateNoteRequest): Call<Note>

    @PUT("/notes/{noteId}")
    fun editNote(@Body note: EditNoteRequest, @Path("noteId") noteId: Int): Call<Note>

    @DELETE("/notes/{noteId}")
    fun deleteNote(@Path("noteId") noteId: Int): Call<Note>
}