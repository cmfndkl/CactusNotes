package com.example.cactusnotes.model

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)