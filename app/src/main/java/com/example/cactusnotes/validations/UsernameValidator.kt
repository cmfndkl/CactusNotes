package com.example.cactusnotes.validations

import com.example.cactusnotes.R
import com.example.cactusnotes.ValidationResult
import com.example.cactusnotes.isNameValid

class UsernameValidator() : Validator {
    override fun value(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, R.string.required)
        } else if (field.length < 2) {
            ValidationResult(false, R.string.userNameSort)
        } else if (field.length > 20) {
            ValidationResult(false, R.string.userNameLong)
        } else if (!field.isNameValid()) {
            ValidationResult(false, R.string.isValidError)
        } else {
            ValidationResult(true, null)
        }
    }
}