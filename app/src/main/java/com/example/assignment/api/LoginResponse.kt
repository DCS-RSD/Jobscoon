package com.example.assignment.api

data class LoginResponse(
    val errors: String,
    val token: String,
    val user: User
)