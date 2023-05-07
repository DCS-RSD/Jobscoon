package com.example.assignment.dataclass

data class Company(
    val name: String,
    val reg_no: String,
    val contact_number: String,
    val email:String,
    val location: String,
    val description: String,
    var email: String? = null,
    var id: Int? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
)