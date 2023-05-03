package com.example.assignment.dataclass

data class ValidationErrorResponse(
    val errors: Any,
    val message: String
)