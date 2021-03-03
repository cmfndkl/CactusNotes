package com.example.cactusnotes

fun String.hasSpecialCharacter() = any { !it.isLetterOrDigit() }

fun String.isNameValid() = all { it.isDigit() || it.isLowerCase() || it == '_' }

fun String.digit() = any { it.isDigit() }

fun String.upperCase() = any { it.isUpperCase() }

fun String.lowerCase() = any { it.isLowerCase() }
