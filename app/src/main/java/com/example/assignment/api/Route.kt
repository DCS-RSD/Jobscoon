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
    @GET("mycompanyprofile")
    fun myCompanyProfile(
        @Header("Authorization") token: String,
    ): Call<Company>

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
    ): Call<Void>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PATCH("updatecompany")
    fun updateCompanyProfile(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("contact_number") contact_number: String,
        @Field("email") email: String,
        @Field("reg_no") regNo: String,
        @Field("location") location: String,
        @Field("description") about: String,
    ): Call<Void>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PATCH("resetpassword")
    fun resetPassword(
        @Header("Authorization") token: String,
        @Field("current_password") currentPassword: String,
        @Field("new_password") newPasswrod: String,
        @Field("new_password_confirmation") newPasswordConfirmation: String,
    ): Call<Void>

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
    fun getCompanyList(): Call<List<CompanyList>>

    /*
    Module: Job Post
     */
    @Headers("Accept: application/json")
    @GET("jobpost")
    fun getJobPost(
        @Header("Authorization") token: String,
    ): Call<List<JobPostItem>>

    @GET("jobpost/{id}")
    fun showJobPost(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<JobPostItem>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("jobpost")
    fun storeJobPost(
        @Header("Authorization") token: String,
        @Field("title") title: String,
        @Field("salary_lower") salary_lower: Int?,
        @Field("salary_upper") salary_upper: Int?,
        @Field("type") type: String,
        @Field("shift_start") shift_start: String,
        @Field("shift_end") shift_end: String,
        @Field("description") description: String,
    ): Call<Void>

    @Headers("Accept: application/json")
    @DELETE("jobpost/{id}")
    fun deleteJobPost(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Call<Void>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PATCH("jobpost/{id}")
    fun updateJobDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("title") title: String?,
        @Field("type") type: String?,
        @Field("shift_start") shift_start: String?,
        @Field("shift_end") shift_end: String?,
        @Field("salary_lower") salary_lower: Int?,
        @Field("salary_upper") salary_upper: Int?,
        @Field("description") description: String?
    ): Call<Void>

    /*
    Module: Job Application
     */
    @GET("jobapplication")
    fun getJobApplication(
        @Header("Authorization") token: String,
    ): Call<List<JobApplicationItem>>

    @GET("jobapplication/{id}")
    fun getJobApplicant(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Call<List<User>>

    @POST("acceptapplication/{id}")
    fun acceptApplication(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<java.lang.Void>

    @POST("declineapplication/{id}")
    fun declineApplication(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Void>



    /*
    Module: Job Interview
     */
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
        @Path("id") id: Int
    ): Call<java.lang.Void>

    @POST("acceptinterview/{id}")
    fun postAcceptInterview(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<java.lang.Void>

    @POST("declineinterview/{id}")
    fun postDeclineInterview(
        @Header("Authorization") token: String,
        @Path("id") id: Int
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
        @Path("id") id: Int
    ): Call<CareerDevelopmentItem>

    /*apply career development
    return message if needed
     */
    @POST("applycareer/{id}")
    fun applyCareer(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<ValidationErrorResponse>

    @POST("cancelcareer/{id}")
    fun cancelCareer(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Void>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("careerdev")
    fun storeCareerDev(
        @Header("Authorization") token: String,
        @Field("title") title: String,
        @Field("date_start") date_start: String,
        @Field("date_end") date_end: String,
        @Field("start_time") start_time: String,
        @Field("end_time") end_time: String,
        @Field("type") type: String,
        @Field("location") location: String,
        @Field("link") link: String,
        @Field("max_capacity") max_capacity: Int?,
        @Field("description") description: String
    ): Call<Void>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PATCH("careerdev/{id}")
    fun updateCareerDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("title") title: String,
        @Field("date_start") date_start: String,
        @Field("date_end") date_end: String,
        @Field("start_time") start_time: String,
        @Field("end_time") end_time: String,
        @Field("type") type: String,
        @Field("location") location: String,
        @Field("link") link: String,
        @Field("max_capacity") max_capacity: Int?,
        @Field("description") description: String
    ): Call<Void>

}
