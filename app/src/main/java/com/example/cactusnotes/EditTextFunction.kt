package com.example.cactusnotes

import com.example.cactusnotes.validations.Validator
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.isFieldValid(validator: Validator): Boolean {
    val validationResult = validator.value(editText!!.text.toString())
    (!validationResult.isValid).also { isErrorEnabled = it }
    validationResult.errorMessage.also { error = it }
    return validationResult.isValid
}