package com.example.cactusnotes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.SignUpActivityBinding
import com.example.cactusnotes.validations.EmailValidator
import com.example.cactusnotes.validations.PasswordValidator
import com.example.cactusnotes.validations.UsernameValidation


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: SignUpActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.setTitle("Sign Up")

        binding.signUpButton.setOnClickListener {
            if (isEmailValid() && isUserNameValidation() && isPasswordValid()) {
                Toast.makeText(applicationContext, R.string.successful, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isEmailValid() = binding.emailEditText.isFieldValid(EmailValidator())

    private fun isPasswordValid() = binding.passwordEditText.isFieldValid(PasswordValidator())

    private fun isUserNameValidation() = binding.userNameEditText.isFieldValid(UsernameValidation())


}


