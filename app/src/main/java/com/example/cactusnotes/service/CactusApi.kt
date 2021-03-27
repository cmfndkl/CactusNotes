package com.example.cactusnotes.service

import com.example.cactusnotes.model.RegisterRequest
import com.example.cactusnotes.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CactusApi {
    @POST("/auth/local/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}