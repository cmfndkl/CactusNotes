package com.example.cactusnotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.ActivityLogInBinding
import com.example.cactusnotes.validations.LogInValidator

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.log_in)

        binding.logInButton.setOnClickListener {
            if (isEmailOrUsernameValid() && isPasswordValid()) {
                Toast.makeText(applicationContext, R.string.log_in, Toast.LENGTH_LONG).show()
            }
        }
        binding.createAccountButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun isEmailOrUsernameValid() = binding.emailOrUsername.isFieldValid(LogInValidator())
    private fun isPasswordValid() = binding.logInPassword.isFieldValid(LogInValidator())
}