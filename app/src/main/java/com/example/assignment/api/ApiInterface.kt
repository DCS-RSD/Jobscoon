package com.example.assignment.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("jobpost")
    fun getJobPost() : Call<List<JobPostItem>>
}