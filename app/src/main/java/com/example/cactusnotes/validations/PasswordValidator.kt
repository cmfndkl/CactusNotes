package com.example.cactusnotes.validations


import com.example.cactusnotes.*

class PasswordValidator : Validator {
    private fun String.passwordValid() = hasDigits() && hasLowerCaseLetters()
            && hasUpperCaseLetters() && hasSpecialLetters()

    override fun value(field: String): ValidationResult {
        return if (field.isEmpty()) {
            ValidationResult(false, R.string.passwordEmpty)
        } else if (field.length < 7) {
            ValidationResult(false, R.string.passwordShort)
        } else if (field.length > 40) {
            ValidationResult(false, R.string.passwordLong)
        } else if (!field.passwordValid()) {
            ValidationResult(false, R.string.passwordSpecialCharacter)
        } else {
            ValidationResult(true, null)
        }
    }
}