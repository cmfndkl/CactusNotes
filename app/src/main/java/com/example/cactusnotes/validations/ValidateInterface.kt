package com.example.cactusnotes.validations

import com.example.cactusnotes.ResultValidation

interface ValidateInterface {
    fun value(field: String): ResultValidation
}