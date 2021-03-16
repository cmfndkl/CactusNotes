package com.example.cactusnotes.validations

import com.example.cactusnotes.R
import com.example.cactusnotes.ValidationResult

class EmailValidator : Validator {
    private fun String.containsAtAndDot() = contains("@") && contains(".")
    override fun validate(field: String) = when {
        field.isEmpty() -> { ValidationResult(false, R.string.required) }
        field.containsAtAndDot().not() -> { ValidationResult(false, R.string.email_invalid) }
        field.length < 5 -> { ValidationResult(false, R.string.email_invalid) }
        field.length > 50 -> { ValidationResult(false, R.string.email_invalid) }
        else -> { ValidationResult(true, null) }
    }
}