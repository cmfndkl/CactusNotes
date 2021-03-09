package com.example.cactusnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import com.example.cactusnotes.databinding.ActivityLogInBinding
import com.example.cactusnotes.validations.EmailValidator
import com.example.cactusnotes.validations.LogInValidator
import java.lang.reflect.Field

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.setTitle("Log In")

        binding.logInButton.setOnClickListener {
            if (isEmailOrUsernameValid() && isPasswordValid()) {
                Toast.makeText(applicationContext, "Log In", Toast.LENGTH_LONG).show()
            }
        }
        binding.createAccountButton.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun isEmailOrUsernameValid() = binding.emailOrUsername.isFieldValid(LogInValidator())
    private fun isPasswordValid() = binding.logInPassword.isFieldValid(LogInValidator())

}