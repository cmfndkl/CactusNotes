package com.example.cactusnotes.validations

import com.example.cactusnotes.binding
import com.example.cactusnotes.isNameValid

fun validateUsername(): Boolean {
    val usernameInput: String = binding.userNameEditText.editText?.text.toString().trim()
    return if (usernameInput.isEmpty()) {
        binding.userNameEditText.error = "Username is required."
        false
    } else if (usernameInput.length < 2) {
        binding.userNameEditText.error = "Username is too short."
        false
    } else if (usernameInput.length > 20) {
        binding.userNameEditText.error = "Username is too long."
        false
    } else if (!usernameInput.isNameValid()) {
        binding.userNameEditText.error = "Username can only include a-z, 0-9 and _ characters."
        false
    } else {
        binding.userNameEditText.error = null
        true
    }
}