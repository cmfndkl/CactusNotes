package com.example.cactusnotes.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityLogInBinding
import com.example.cactusnotes.login.data.LoginErrorResponse
import com.example.cactusnotes.login.data.LoginRequest
import com.example.cactusnotes.login.data.LoginResponse
import com.example.cactusnotes.note.list.NoteListActivity
import com.example.cactusnotes.service.api
import com.example.cactusnotes.signup.SignUpActivity
import com.example.cactusnotes.userstore.UserStore
import com.example.cactusnotes.validations.LogInPasswordValidator
import com.example.cactusnotes.validations.LogInValidator
import com.example.cactusnotes.validations.isFieldValid
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.Response

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.setTitle(R.string.log_in)

        binding.logInButton.setOnClickListener {
            if (isEmailOrUsernameValid() && isPasswordValid()) {
                sendLoginRequest()
            }
        }
        binding.createAccountButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun isEmailOrUsernameValid() = binding.emailOrUsername.isFieldValid(LogInValidator())

    private fun isPasswordValid() = binding.logInPassword.isFieldValid(LogInPasswordValidator())

    private fun sendLoginRequest() {
        val loginRequest = LoginRequest(
            binding.emailOrUsername.editText!!.text.toString(),
            binding.logInPassword.editText!!.text.toString()
        )
        api.login(loginRequest).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    saveJwt(response.body()!!.jwt)
                    navigateToNoteList()
                } else {
                    val errorBody = response.errorBody()!!.string()
                    val errorResponse = try {
                        GsonBuilder()
                            .create()
                            .fromJson(errorBody, LoginErrorResponse::class.java)
                    } catch (ex: JsonSyntaxException) {
                        null
                    }
                    val errorMessageToDisplay = when (errorResponse) {
                        null -> getString(R.string.unexpected_error_occurred)
                        else -> {
                            //val message = errorResponse.message[0].messages[0].message
                            when (errorResponse.statusCode) {
                                in 400..499 -> getString(R.string.invalid_login_message)
                                in 500..599 -> getString(R.string.some_error_message)
                                else -> getString(R.string.unexpected_error_occurred)
                            }
                        }
                    }
                    showSnackbar(errorMessageToDisplay)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showSnackbar(getString(R.string.connectivity_problems_message))
            }
        })
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun saveJwt(jwt: String) {
        val userStore = UserStore(this@LogInActivity)
        userStore.saveJwt(jwt)
    }

    private fun navigateToNoteList() {
        startActivity(Intent(this@LogInActivity, NoteListActivity::class.java))
        finish()
    }
}