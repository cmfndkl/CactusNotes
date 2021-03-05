package com.example.cactusnotes

import com.example.cactusnotes.validations.ValidateInterface
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.isFieldValue(validator: ValidateInterface): Boolean {
    val editText = editText
    val field = editText!!.text.toString()
    val validationResult = validator.value(field)
    isErrorEnabled = !validationResult.isValid
    error = validationResult.errorMessage
    return validationResult.isValid
}