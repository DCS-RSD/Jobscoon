package com.example.assignment.dataclass

data class JobInterviewItem(
    val combined_time: String,
    val created_at: String,
    val date: String,
    val day_in_word: String,
    val day_only: String,
    val description: String,
    val end_time: String,
    val id: Int,
    val job_post: JobPostItem,
    val job_post_id: Int,
    val link: String,
    val location: String,
    val month_in_word: String,
    val start_time: String,
    val type: String,
    val status: String,
    val updated_at: String,
    val user_id: Int,
    val year_only: String
)