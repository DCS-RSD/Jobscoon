package com.example.assignment.api

data class Errors(
    val email: List<String>,
    val password: List<String>
)