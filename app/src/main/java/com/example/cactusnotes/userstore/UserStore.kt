package com.example.cactusnotes.userstore

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class UserStore(private val context: Context) {
    fun saveJwt(jwt: String) {
        sharedPrefs().edit(commit = true) {
            putString(KEY_JWT, jwt)
        }
    }

    fun getJwt() = sharedPrefs().getString(KEY_JWT, null)

    fun deleteJwt() {
        sharedPrefs().edit(commit = true) {
            remove(KEY_JWT)
        }
    }

    private fun sharedPrefs() = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)

    companion object {
        const val KEY_JWT = "jwt"
        const val FILE_NAME = "userStore"
    }
}