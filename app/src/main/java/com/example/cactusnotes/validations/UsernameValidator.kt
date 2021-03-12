package com.example.cactusnotes.validations

import com.example.cactusnotes.R
import com.example.cactusnotes.ValidationResult

class UsernameValidator : Validator {
    override fun validate(field: String) = when {
        field.isEmpty() -> {
            ValidationResult(false, R.string.user_name_required)
        }
        field.length < 2 -> {
            ValidationResult(false, R.string.user_name_sort)
        }
        field.length > 20 -> {
            ValidationResult(false, R.string.user_name_long)
        }
        !field.containsOnlyAllowedCharacters() -> {
            ValidationResult(false, R.string.is_valid_error)
        }
        else -> {
            ValidationResult(true, null)
        }
    }

    private fun String.containsOnlyAllowedCharacters() =
        all { it.isDigit() || it.isLowerCase() || it == '_' }
}