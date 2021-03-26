package com.example.cactusnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.SignUpActivityBinding
import com.example.cactusnotes.model.RegisterErrorResponse
import com.example.cactusnotes.model.RegisterResponse
import com.example.cactusnotes.service.api
import com.example.cactusnotes.validations.EmailValidator
import com.example.cactusnotes.validations.PasswordValidator
import com.example.cactusnotes.validations.UsernameValidator
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
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
            try {
                if (isEmailValid() && isUsernameValid() && isPasswordValid()) {
                    val request = CactusModel(
                        binding.emailEditText.editText?.text.toString(),
                        binding.userNameEditText.editText?.text.toString(),
                        binding.passwordEditText.editText?.text.toString()
                    )

                    api.register(request).enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (response.isSuccessful) {
                                Snackbar.make(
                                    binding.root,
                                    R.string.successful,
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()
                            } else {
                                val errorBody = response.errorBody()!!.string()
                                val errorResponse = GsonBuilder()
                                    .create()
                                    .fromJson(errorBody, RegisterErrorResponse::class.java)
                                val message = errorResponse.message[0].messages[0].message
                                val statusCode = errorResponse.statusCode
                                if (statusCode in 400..499) {
                                    Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
                                        .show()
                                } else if (statusCode in 500..599) {
                                    Snackbar.make(
                                        binding.root,
                                        getString(R.string.some_error_message),
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Snackbar.make(
                                binding.root,
                                getString(R.string.connectivity_problems_message),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    })
                }
            } catch (ex: IllegalArgumentException) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.other_error_message),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        binding.alreadyHaveAnAccountButton.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }
    }

    private fun isEmailValid() = binding.emailEditText.isFieldValid(EmailValidator())

    private fun isPasswordValid() = binding.passwordEditText.isFieldValid(PasswordValidator())

    private fun isUsernameValid() = binding.userNameEditText.isFieldValid(UsernameValidator())
}


