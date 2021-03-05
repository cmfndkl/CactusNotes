package com.example.cactusnotes.validations

import com.example.Validaitons.isNameValid
import com.example.cactusnotes.ResultValidation

class UsernameValidation() : ValidateInterface {
    override fun value(field: String): ResultValidation {
        return if (field.isEmpty()) {
            ResultValidation(false, "Username is required")
        } else if (field.length < 2) {
            ResultValidation(false, "Username is too short.")
        } else if (field.length > 20) {
            ResultValidation(false, "Username is too long.")
        } else if (!field.isNameValid()) {
            ResultValidation(false, "Username can only include a–z, 0–9 and _ characters.")
        } else {
            ResultValidation(true, null)
        }
    }
}