package com.example.assignment.dataclass

data class JobApplicationItem(
    val created_at: String,
    val id: Int,
    val job_post_id: Int,
    val status: String,
    val updated_at: String,
    val user_id: Int,
    val job_post: JobPostItem,
    val applied_at: String
)