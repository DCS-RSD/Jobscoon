package com.example.assignment.api

import com.example.assignment.auth.LoginResponse
import com.example.assignment.dataclass.JobPostItem
import retrofit2.Call
import retrofit2.http.*

interface Route {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @GET("jobpost")
    fun getJobPost(
        @Header("Authorization") token : String,
    ) : Call<List<JobPostItem>>
    @POST("jobpost")
    fun createJobPost(@Body jobPostItem: JobPostItem) : Call<JobPostItem>
}