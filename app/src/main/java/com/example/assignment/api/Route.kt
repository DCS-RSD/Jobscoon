package com.example.assignment.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Route {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @GET("jobpost")
    fun getJobPost() : Call<List<JobPostItem>>
    @POST("jobpost")
    fun createJobPost(@Body jobPostItem: JobPostItem) : Call<JobPostItem>
}