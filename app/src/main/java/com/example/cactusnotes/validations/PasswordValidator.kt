package com.example.cactusnotes.validations


import com.example.cactusnotes.*

class PasswordValidator : Validator {
    private fun String.passwordValid() = hasDigit() && hasLowerCaseLetters()
            && hasUpperCaseLetters() && hasSpecialLetters()

    override fun value(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, "Password is required.")
        } else if (field.length < 7) {
            ValidationResult(false, "Password is too short.")
        } else if (field.length > 40) {
            ValidationResult(false, "Password is too long.")
        } else if (!field.passwordValid()) {
            ValidationResult(
                false, "Password must contain one digit, one uppercase letter, " +
                        "one lowercase letter and one special character."
            )
        } else {
            ValidationResult(true, null)
        }
    }
}