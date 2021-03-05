package com.example.cactusnotes.validations

import com.example.Validaitons.hasDigitCase
import com.example.Validaitons.hasLowerCaseLetters
import com.example.Validaitons.hasSpecialCharacter
import com.example.Validaitons.hasUpperCaseLetters
import com.example.cactusnotes.ResultValidation

class PasswordValidator : ValidateInterface {
    private fun String.specialPasswordValid() = hasDigitCase() && hasLowerCaseLetters()
            && hasUpperCaseLetters() && hasSpecialCharacter()

    override fun value(field: String): ResultValidation {
        return if (field.isEmpty()) {
            ResultValidation(false, "Password is required.")
        } else if (field.length < 7) {
            ResultValidation(false, "Password is too short.")
        } else if (field.length > 40) {
            ResultValidation(false, "Password is too long.")
        } else if (!field.specialPasswordValid()) {
            ResultValidation(
                false, "Password must contain one digit, one uppercase letter, " +
                        "one lowercase letter and one special character."
            )
        } else {
            ResultValidation(true, null)
        }
    }
}