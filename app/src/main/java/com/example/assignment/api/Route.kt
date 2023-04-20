package com.example.assignment.api

import com.example.assignment.auth.LoginResponse
import com.example.assignment.auth.SignUpItem
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

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("description") about: String,
        @Field("name") name: String,
    ):Call<SignUpItem>


    @GET("jobpost")
    fun getJobPost(
        @Header("Authorization") token : String,
    ) : Call<List<JobPostItem>>
    @POST("jobpost")
    fun createJobPost(@Body jobPostItem: JobPostItem) : Call<JobPostItem>
}