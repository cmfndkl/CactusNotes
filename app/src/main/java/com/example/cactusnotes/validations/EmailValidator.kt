package com.example.cactusnotes.validations

import com.example.cactusnotes.ValidationResult

class EmailValidator() : Validator {
    private fun String.emailValid() = contains("@") && contains(".")
    override fun value(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, "Email is required.")
        } else if (field.emailValid().not()) {
            ValidationResult(false, "Email is invalid.")
        } else if (field.length < 5) {
            ValidationResult(false, "Email is invalid")
        } else if (field.length > 50) {
            ValidationResult(false, "Email is invalid")
        } else {
            ValidationResult(true, null)
        }
    }
}