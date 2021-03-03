package com.example.cactusnotes

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cactusnotes.databinding.SignUpActivityBinding
import com.example.cactusnotes.validations.validateEmail
import com.example.cactusnotes.validations.validatePassword
import com.example.cactusnotes.validations.validateUsername
import java.util.regex.Pattern


lateinit var binding: SignUpActivityBinding

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.signUpButton.setOnClickListener {
            if (validateEmail() && validateUsername() && validatePassword()) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.successful),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


