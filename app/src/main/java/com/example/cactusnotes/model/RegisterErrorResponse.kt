package com.example.cactusnotes.model

data class RegisterErrorResponse(
    val statusCode: Int,
    val message: List<Message>
)

