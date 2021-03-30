package com.example.cactusnotes.validations

import com.example.cactusnotes.R
import com.example.cactusnotes.ValidationResult

class LogInValidator : Validator {
    override fun validate(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, R.string.email_required)
        } else {
            ValidationResult(true, null)
        }
    }
}