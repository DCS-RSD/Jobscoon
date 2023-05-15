package com.example.assignment.dataclass

data class User(
    var address: String,
    var description: String,
    val email: String,
    val phone: String,
    var name: String,

    var email_verified_at: String? = null,
    var company_id: Int? = null,
    var id: Int? = null,
    var is_employer: Int? = null,
    var updated_at: String? = null,
    var created_at: String? = null,
    var company: Company? = null,
    var pivot: JobApplicationItem? = null,
)