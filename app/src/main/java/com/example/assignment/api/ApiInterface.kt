package com.example.assignment.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("jobpost")
    fun getJobPost() : Call<List<JobPostItem>>

    @POST("jobpost")
    fun createJobPost(@Body jobPostItem: JobPostItem) : Call<JobPostItem>
}