package com.example.assignment.api

data class LoginResponse(
    val message : String,
    val token: String,
    val user: User,
)