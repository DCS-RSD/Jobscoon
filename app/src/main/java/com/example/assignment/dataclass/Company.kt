package com.example.assignment.dataclass

data class Company(
    val name: String,
    val reg_no: String,
    val contact_number: String,
    val location: String,
    val description: String,
    val email: String ,
    var id: Int? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
)