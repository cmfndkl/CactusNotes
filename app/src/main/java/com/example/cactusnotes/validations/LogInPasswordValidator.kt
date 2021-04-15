package com.example.cactusnotes.validations

import com.example.cactusnotes.R

class LogInPasswordValidator : Validator {
    override fun validate(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, R.string.password_empty)
        } else {
            ValidationResult(true, null)
        }
    }
}