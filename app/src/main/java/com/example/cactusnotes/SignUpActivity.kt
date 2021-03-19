package com.example.cactusnotes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.SignUpActivityBinding
import com.example.cactusnotes.validations.EmailValidator
import com.example.cactusnotes.validations.PasswordValidator
import com.example.cactusnotes.validations.UsernameValidator

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: SignUpActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.sign_up)

        binding.signUpButton.setOnClickListener {
            if (isEmailValid() && isUsernameValid() && isPasswordValid()) {
                Toast.makeText(applicationContext, R.string.successful, Toast.LENGTH_LONG).show()
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


