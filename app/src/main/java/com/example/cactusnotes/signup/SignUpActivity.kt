package com.example.cactusnotes.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.R
import com.example.cactusnotes.databinding.SignUpActivityBinding
import com.example.cactusnotes.login.LogInActivity
import com.example.cactusnotes.service.api
import com.example.cactusnotes.signup.data.RegisterErrorResponse
import com.example.cactusnotes.signup.data.RegisterRequest
import com.example.cactusnotes.signup.data.RegisterResponse
import com.example.cactusnotes.userstore.UserStore
import com.example.cactusnotes.validations.EmailValidator
import com.example.cactusnotes.validations.PasswordValidator
import com.example.cactusnotes.validations.UsernameValidator
import com.example.cactusnotes.validations.isFieldValid
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: SignUpActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.sign_up)
        binding.signUpButton.setOnClickListener {
            if (isEmailValid() && isUsernameValid() && isPasswordValid()) {
                sendRegisterRequest()
            }
        }
        binding.alreadyHaveAnAccountButton.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }

    private fun sendRegisterRequest() {
        val request = RegisterRequest(
            binding.emailEditText.editText!!.text.toString(),
            binding.userNameEditText.editText!!.text.toString(),
            binding.passwordEditText.editText!!.text.toString()
        )

        api.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Snackbar.make(binding.root, R.string.successful, LENGTH_LONG).show()
                    val userStore = UserStore(this@SignUpActivity)
                    userStore.saveJwt(response.body()!!.jwt)
                } else {
                    val errorBody = response.errorBody()!!.string()
                    val errorResponse = try {
                        GsonBuilder()
                            .create()
                            .fromJson(errorBody, RegisterErrorResponse::class.java)
                    } catch (ex: JsonSyntaxException) {
                        null
                    }
                    val errorMessageToDisplay = when (errorResponse) {
                        null -> getString(R.string.unexpected_error_occurred)
                        else -> {
                            val message = errorResponse.message[0].messages[0].message
                            when (errorResponse.statusCode) {
                                in 400..499 -> message
                                in 500..599 -> getString(R.string.some_error_message)
                                else -> getString(R.string.unexpected_error_occurred)
                            }
                        }
                    }
                    Snackbar.make(binding.root, errorMessageToDisplay, LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.connectivity_problems_message),
                    LENGTH_LONG
                ).show()
            }
        })
    }

    private fun isEmailValid() = binding.emailEditText.isFieldValid(EmailValidator())

    private fun isPasswordValid() = binding.passwordEditText.isFieldValid(PasswordValidator())

    private fun isUsernameValid() = binding.userNameEditText.isFieldValid(UsernameValidator())
}


