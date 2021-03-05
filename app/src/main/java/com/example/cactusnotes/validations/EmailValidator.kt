package com.example.cactusnotes.validations

import com.example.cactusnotes.ResultValidation

class EmailValidator() : ValidateInterface {

    private fun String.emailValid() = contains("@") && contains(".")

    override fun value(field: String): ResultValidation {
        return if (field.isEmpty()) {
            ResultValidation(false, "Email is required.")
        } else if (field.emailValid().not()) {
            ResultValidation(false, "Email is invalid.")
        } else if (field.length < 5) {
            ResultValidation(false, "Email is invalid")
        } else if (field.length > 50) {
            ResultValidation(false, "Email is invalid")
        } else {
            ResultValidation(true, null)
        }
    }
}