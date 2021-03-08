package com.example.cactusnotes

fun String.hasSpecialLetters() = any { !it.isLetterOrDigit() }

fun String.isNameValid() = all { it.isDigit() || it.isLowerCase() || it == '_' }

fun String.hasDigit() = any { it.isDigit() }

fun String.hasUpperCaseLetters() = any { it.isUpperCase() }

fun String.hasLowerCaseLetters() = any { it.isLowerCase() }
