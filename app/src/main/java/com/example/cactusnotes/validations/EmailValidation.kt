package com.example.cactusnotes.validations

import android.util.Patterns
import com.example.cactusnotes.binding

private fun String.emailValid() = contains("@") && contains(".")

fun validateEmail(): Boolean {
    val emailInput: String = binding.emailEditText.editText?.text.toString().trim()
    return if (emailInput.isEmpty()) {
        binding.emailEditText.error = "Email is required"
        false
    } else if (!emailInput.emailValid()) {
        binding.emailEditText.error = "Email is invalid."
        false
    } else if (emailInput.length < 5 || emailInput.length > 50) {
        binding.emailEditText.error = "Email is invalid."
        false
    } else {
        binding.emailEditText.error = null
        true
    }
}
