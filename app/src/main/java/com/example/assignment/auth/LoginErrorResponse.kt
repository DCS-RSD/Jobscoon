package com.example.assignment.auth

data class LoginErrorResponse(
    val errors: Errors,
    val message: String
)