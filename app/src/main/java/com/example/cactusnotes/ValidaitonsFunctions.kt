package com.example.Validaitons

fun String.hasSpecialCharacter() = any { !it.isLetterOrDigit() }

fun String.isNameValid() = all { it.isDigit() || it.isLowerCase() || it == '_' }

fun String.hasDigitCase() = any { it.isDigit() }

fun String.hasUpperCaseLetters() = any { it.isUpperCase() }

fun String.hasLowerCaseLetters() = any { it.isLowerCase() }
