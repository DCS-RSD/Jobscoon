package com.example.assignment.dataclass

data class JobPostItem(
    var company_id: Int?=null,
    var created_at: String?=null,
    var description: String?=null,
    var id: Int?=null,
    var salary_lower: Int?=null,
    var salary_upper: Int?=null,
    var shift: String?=null,
    var title: String?=null,
    var type: String?=null,
    var updated_at: String?=null,
    var post_at: String?=null,
    var salary: String?=null,
    var company: Company?=null,
    var is_applied: Boolean?=null,
)