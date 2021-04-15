package com.example.cactusnotes.login.data


data class LoginErrorResponse(
    val statusCode: Int,
    val message: List<LoginMessage>
)
