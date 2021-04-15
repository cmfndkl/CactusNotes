package com.example.cactusnotes.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.ActivityLogInBinding
import com.example.cactusnotes.isFieldValid
import com.example.cactusnotes.login.data.LoginErrorResponse
import com.example.cactusnotes.login.data.LoginRequest
import com.example.cactusnotes.login.data.LoginResponse
import com.example.cactusnotes.service.api
import com.example.cactusnotes.signup.SignUpActivity
import com.example.cactusnotes.userstore.UserStore
import com.example.cactusnotes.validations.LogInValidator
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

    private fun isPasswordValid() = binding.logInPassword.isFieldValid(LogInValidator())

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
                    Snackbar.make(binding.root, R.string.successful, Snackbar.LENGTH_LONG).show()
                    val userStore = UserStore(this@LogInActivity)
                    userStore.saveJwt(response.body()!!.jwt)
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
                    Snackbar.make(binding.root, errorMessageToDisplay, Snackbar.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.connectivity_problems_message),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }
}