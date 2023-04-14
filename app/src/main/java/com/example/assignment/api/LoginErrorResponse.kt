package com.example.assignment.api

data class LoginErrorResponse(
    val errors: Errors,
    val message: String
)