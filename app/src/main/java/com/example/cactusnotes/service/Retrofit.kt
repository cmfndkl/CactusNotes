package com.example.cactusnotes.service

import android.content.Context
import com.example.cactusnotes.userstore.UserStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private var _api: CactusApi? = null
val api: CactusApi
    get() = _api!!

fun generateApi(context: Context) {
    val userStore = UserStore(context)
    val authInterceptor = AuthInterceptor(userStore)
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = BODY

    val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    _api = Retrofit.Builder()
        .baseUrl("https://apps.cactus.school")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CactusApi::class.java)
}


