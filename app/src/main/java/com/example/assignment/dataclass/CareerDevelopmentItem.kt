package com.example.assignment.dataclass

data class CareerDevelopmentItem(
    val title: String?=null,
    val date_start: String?=null,
    val date_end: String?=null,
    val start_time: String?=null,
    val end_time: String?=null,
    val type: String?=null,
    val location: String?=null,
    val link: String?=null,
    val max_capacity: Int?=null,
    val description: String?=null,
    val combined_time: String?=null,
    val capacity: Int?=null,
    val company: Company?=null,
    val company_id: Int?=null,
    val created_at: String?=null,
    val date_only: String?=null,
    val id: Int?=null,
    val updated_at: String?=null,
    var is_applied:Boolean?=null,
)