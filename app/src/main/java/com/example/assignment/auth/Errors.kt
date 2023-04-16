package com.example.assignment.auth

data class Errors(
    val email: List<String>,
    val password: List<String>
)