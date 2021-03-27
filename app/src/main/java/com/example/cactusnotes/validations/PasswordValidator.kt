package com.example.cactusnotes.validations

import com.example.cactusnotes.*

class PasswordValidator : Validator {

    override fun validate(field: String) = when {
        field.isEmpty() -> {
            ValidationResult(false, R.string.password_empty)
        }
        field.length < 7 -> {
            ValidationResult(false, R.string.password_short)
        }
        field.length > 40 -> {
            ValidationResult(false, R.string.password_long)
        }
        !field.containsRequiredCharacters() -> {
            ValidationResult(false, R.string.password_special_character)
        }
        else -> {
            ValidationResult(true, null)
        }
    }

    private fun String.hasDigits() = any { it.isDigit() }

    private fun String.hasUpperCaseLetters() = any { it.isUpperCase() }

    private fun String.hasLowerCaseLetters() = any { it.isLowerCase() }

    private fun String.hasSpecialLetters() = any { !it.isLetterOrDigit() }

    private fun String.containsRequiredCharacters() = hasDigits() && hasLowerCaseLetters()
            && hasUpperCaseLetters() && hasSpecialLetters()

}