package com.example.cactusnotes.service

import com.example.cactusnotes.model.CactusModel
import com.example.cactusnotes.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CactusApi {
    @POST("/auth/local/register")
    fun register(@Body request: CactusModel): Call<RegisterResponse>
}