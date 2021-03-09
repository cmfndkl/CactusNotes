package com.example.cactusnotes.validations

import com.example.cactusnotes.ValidationResult

interface Validator {
    fun value(field: String): ValidationResult
}