package com.example.cactusnotes.signup.data

data class RegisterErrorResponse(
    val statusCode: Int,
    val message: List<Message>
)

