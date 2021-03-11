package com.example.cactusnotes.validations

import com.example.cactusnotes.R
import com.example.cactusnotes.ValidationResult

class EmailValidator() : Validator {
    private fun String.isEmailValid() = contains("@") && contains(".")

    override fun value(field: String): ValidationResult {

        return if (field.isEmpty()) {
            ValidationResult(false, R.string.required)
        } else if (field.isEmailValid().not()) {
            ValidationResult(false, R.string.emailInvalid)
        } else if (field.length < 5) {
            ValidationResult(false, R.string.emailInvalid)
        } else if (field.length > 50) {
            ValidationResult(false, R.string.emailInvalid)
        } else {
            ValidationResult(true, null)
        }
    }
}