package com.example.cactusnotes.validations

import com.example.cactusnotes.*

fun String.specialPasswordValid() = digit() && lowerCase() && upperCase() && hasSpecialCharacter()

fun validatePassword(): Boolean {
    val passwordInput: String = binding.passwordEditText.editText?.text.toString().trim()
    return if (passwordInput.isEmpty()) {
        binding.passwordEditText.error = "Password is required."
        false
    } else if (passwordInput.length < 7) {
        binding.passwordEditText.error = "Password is too short."
        false
    } else if (passwordInput.length > 40) {
        binding.passwordEditText.error = "Password is too long."
        false
    } else if (!passwordInput.specialPasswordValid()) {
        binding.passwordEditText.error =
            "Password must contain one digit, one uppercase letter, " +
                    "one lowercase letter and one special character."
        false
    } else {
        binding.passwordEditText.error = null
        true
    }
}

