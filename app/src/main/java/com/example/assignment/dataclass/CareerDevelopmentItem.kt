package com.example.assignment.dataclass

data class CareerDevelopmentItem(
    val capacity: Int?=null,
    val combined_time: String?=null,
    val company: Company?=null,
    val company_id: Int?=null,
    val created_at: String?=null,
    val date_end: String?=null,
    val date_only: String?=null,
    val date_start: String?=null,
    val description: String?=null,
    val end_time: String?=null,
    val id: Int?=null,
    val link: String?=null,
    val location: String?=null,
    val start_time: String?=null,
    val title: String?=null,
    val type: String?=null,
    val updated_at: String?=null,
    var is_applied:Boolean?=null,
)