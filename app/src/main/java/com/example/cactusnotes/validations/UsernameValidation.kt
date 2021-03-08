package com.example.cactusnotes.validations

import com.example.cactusnotes.ValidationResult
import com.example.cactusnotes.isNameValid

class UsernameValidation() : Validator {
    override fun value(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, "Username is required")
        } else if (field.length < 2) {
            ValidationResult(false, "Username is too short.")
        } else if (field.length > 20) {
            ValidationResult(false, "Username is too long.")
        } else if (!field.isNameValid()) {
            ValidationResult(false, "Username can only include a–z, 0–9 and _ characters.")
        } else {
            ValidationResult(true, null)
        }
    }
}