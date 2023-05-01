package com.example.assignment.dataclass

data class User(
    val address: String,
    val company_id: Int,
    val created_at: String,
    val description: String,
    val email: String,
    val email_verified_at: String,
    val id: Int,
    val is_employer: Int,
    val name: String,
    val phone: String,
    val updated_at: String
)