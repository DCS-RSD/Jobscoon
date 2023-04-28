package com.example.assignment.auth

data class SignUpItem(
    val address: String,
    val description: String,
    val email: String,
    val name: String,
    val password: String,
    val phone: String,

    var company_name: String?=null,
    var reg_no: String?=null,
    var contact_number: String?=null,
    var location: String?=null,
    var company_description: String?=null,
)