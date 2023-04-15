package com.example.assignment.api

data class JobPostItem(
    val company_id: Int,
    val created_at: String,
    val description: String,
    val id: Int?,
    val salary_lower: Int,
    val salary_upper: Int,
    val shift: String,
    val title: String,
    val type: String,
    val updated_at: String,
    val post_at: String,
)