package com.example.cactusnotes.validations

import com.example.cactusnotes.ValidationResult

class LogInValidator() : Validator {
    override fun value(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, "Email or username is required.")
        } else {
            ValidationResult(true, null)
        }
    }
}