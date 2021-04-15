package com.example.cactusnotes

import com.example.cactusnotes.validations.Validator
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.isFieldValid(validator: Validator): Boolean {
    val validationResult = validator.validate(editText!!.text.toString())
    isErrorEnabled = !validationResult.isValid
    error = validationResult.errorMessage?.let { resources.getString(it) }
    return validationResult.isValid
}