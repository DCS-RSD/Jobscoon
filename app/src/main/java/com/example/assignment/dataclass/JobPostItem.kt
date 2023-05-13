package com.example.assignment.dataclass

data class JobPostItem(
    var title: String? = null,
    var type: String? = null,
    var shift_start: String? = null,
    var shift_end: String? = null,
    var salary_lower: Int? = null,
    var salary_upper: Int? = null,
    var description: String? = null,
    var company_id: Int? = null,
    var created_at: String? = null,
    var id: Int? = null,
    var shift: String? = null,
    var updated_at: String? = null,
    var post_at: String? = null,
    var salary: String? = null,
    var company: Company? = null,
    var is_applied: Boolean? = null

)