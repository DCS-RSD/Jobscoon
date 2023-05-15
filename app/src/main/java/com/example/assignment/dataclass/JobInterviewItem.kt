package com.example.assignment.dataclass

data class JobInterviewItem(
    val combined_time: String? = null,
    val created_at: String? = null,
    val date: String? = null,
    val day_in_word: String? = null,
    val day_only: String? = null,
    val description: String? = null,
    val end_time: String? = null,
    val id: Int?=null,
    val job_post: JobPostItem? = null,
    val job_post_id: Int? = null,
    val link: String? = null,
    val location: String? = null,
    val month_in_word: String? = null,
    val start_time: String? = null,
    val type: String? = null,
    val status: String? = null,
    val updated_at: String? = null,
    val user_id: Int? = null,
    val year_only: String? = null,
    val user: User? = null,
)