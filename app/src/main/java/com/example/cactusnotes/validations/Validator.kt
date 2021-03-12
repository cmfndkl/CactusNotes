package com.example.cactusnotes.validations

import com.example.cactusnotes.ValidationResult

interface Validator {
    fun validate(field: String): ValidationResult
}