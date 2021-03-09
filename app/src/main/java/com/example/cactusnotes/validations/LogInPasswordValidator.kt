package com.example.cactusnotes.validations

import com.example.cactusnotes.ValidationResult

class LogInPasswordValidator : Validator {
    override fun value(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, "Password is required.")
        } else {
            ValidationResult(true, null)
        }
    }

}