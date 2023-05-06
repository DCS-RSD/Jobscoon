package com.example.assignment.api

import com.example.assignment.auth.LoginResponse
import com.example.assignment.auth.SignUpItem
import com.example.assignment.dataclass.*
import retrofit2.Call
import retrofit2.http.*

interface Route {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

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

        //for employer
        @Field("is_employer") isEmployer: Boolean? = null,
        @Field("company_name") companyName: String? = null,
        @Field("contact_number") contactNumber: String? = null,
        @Field("reg_no") regNo: String? = null,
        @Field("company_location") location: String? = null,
        @Field("company_description") companyAbout: String? = null,
    ): Call<SignUpItem>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("autologin")
    fun autoLogin(
        @Header("Authorization") token: String,
        @Field("id") id : String,
    ): Call<User>

    @Headers("Accept: application/json")
    @POST("logout")
    fun logout(
        @Header("Authorization") token: String,
    ): Call<LoginResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("validatecompany")
    fun validateCompany(
        @Field("company_name") name: String,
        @Field("contact_number") phone: String,
        @Field("reg_no") regNo: String,
        @Field("company_location") location: String,
        @Field("company_description") about: String,
    ): Call<Company>


    @GET("jobpost")
    fun getJobPost(
        @Header("Authorization") token: String,
    ): Call<List<JobPostItem>>

    @GET("jobpost/{id}")
    fun showJobPost(
        @Header("Authorization") token: String,
        @Path("id") id:Int
    ): Call<JobPostItem>

    @POST("jobpost")
    fun createJobPost(@Body jobPostItem: JobPostItem): Call<JobPostItem>

    @GET("jobapplication")
    fun getJobApplication(
        @Header("Authorization") token: String,
    ): Call<List<JobApplicationItem>>

    @GET("jobinterview")
    fun getJobInterview(
        @Header("Authorization") token: String,
    ): Call<List<JobInterviewItem>>

    @GET("careerdev")
    fun getCareerDevelopment(
        @Header("Authorization") token: String,
    ): Call<List<CareerDevelopmentItem>>

}