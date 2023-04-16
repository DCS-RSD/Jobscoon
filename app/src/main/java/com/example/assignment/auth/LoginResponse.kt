package com.example.assignment.auth

import com.example.assignment.dataclass.User

data class LoginResponse(
    val message : String,
    val token: String,
    val user: User,
)