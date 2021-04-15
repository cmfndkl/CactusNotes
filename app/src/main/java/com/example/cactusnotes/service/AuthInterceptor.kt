package com.example.cactusnotes.service

import com.example.cactusnotes.userstore.UserStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userStore: UserStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        // If token has been saved, add it to the request
        userStore.getJwt()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}