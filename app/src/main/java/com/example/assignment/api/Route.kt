package com.example.assignment.api

import com.example.assignment.auth.CompanyList
import com.example.assignment.auth.LoginResponse
import com.example.assignment.auth.SignUpItem
import com.example.assignment.dataclass.*
import retrofit2.Call
import retrofit2.http.*

interface Route {

    /*
    Module: Auth
     */
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
        @Field("company_email") companyEmail: String? = null,
        @Field("reg_no") regNo: String? = null,
        @Field("company_location") location: String? = null,
        @Field("company_description") companyAbout: String? = null,

        @Field("is_new_company") isNewCompany: Boolean? = null,
        @Field("search_company") searchCompany: String? = null,

        ): Call<SignUpItem>

    @Headers("Accept: application/json")
    @GET("myprofile")
    fun myProfile(
        @Header("Authorization") token: String,
    ): Call<User>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PATCH("updateprofile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("description") about: String,
        @Field("name") name: String,
    ):Call<Void>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PATCH("resetpassword")
    fun resetPassword(
        @Header("Authorization") token: String,
        @Field("current_password") currentPassword: String,
        @Field("new_password") newPasswrod: String,
        @Field("new_password_confirmation") newPasswordConfirmation: String,
    ):Call<Void>

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
        @Field("company_email") email: String?,
        @Field("reg_no") regNo: String,
        @Field("company_location") location: String,
        @Field("company_description") about: String,
    ): Call<Company>

    @GET("companylist")
    fun getCompanyList():Call<List<CompanyList>>

    @Headers("Accept: application/json")
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

    @POST("applyjob/{id}")
    fun postJobApplication(
        @Header("Authorization") token: String,
        @Path("id") id:Int
    ): Call<java.lang.Void>

    @POST("acceptinterview/{id}")
    fun postAcceptInterview(
        @Header("Authorization") token: String,
        @Path("id") id:Int
    ): Call<java.lang.Void>

    @POST("declineinterview/{id}")
    fun postDeclineInterview(
        @Header("Authorization") token: String,
        @Path("id") id:Int
    ): Call<Void>


    /*
    Module: Career Development
    Route: resource, apply, cancel
     */

    // index
    @GET("careerdev")
    fun getCareer(
        @Header("Authorization") token: String,
    ): Call<List<CareerDevelopmentItem>>

    // show
    @GET("careerdev/{id}")
    fun showCareer(
        @Header("Authorization") token: String,
        @Path("id") id:Int
    ): Call<CareerDevelopmentItem>

    /*apply career development
    return message if needed
     */
    @POST("applycareer/{id}")
    fun applyCareer(
        @Header("Authorization") token: String,
        @Path("id") id:Int
    ): Call<ValidationErrorResponse>

    @POST("cancelcareer/{id}")
    fun cancelCareer(
        @Header("Authorization") token: String,
        @Path("id") id:Int
    ): Call<Void>

}
