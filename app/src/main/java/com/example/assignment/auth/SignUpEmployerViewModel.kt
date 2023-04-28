package com.example.assignment.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.Company
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.ValidationErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpEmployerViewModel : ViewModel() {
    val signUpResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    lateinit var companyDetails: Company

    fun submitCompany(input: Company) {

        val build = RetrofitBuild.build().validateCompany(
            input.name,
            input.contact_number,
            input.reg_no,
            input.location,
            input.description,
        )

        build.enqueue(object : Callback<Company?> {
            override fun onResponse(call: Call<Company?>, response: Response<Company?>) {
                if (response.isSuccessful) {
                    signUpResponse.value = ResponseForUI(true, "")
                    companyDetails = input
                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ValidationErrorResponse::class.java
                    )
                    signUpResponse.value = ResponseForUI(false, error.message)
                    Log.d("login", "onResponse: $error")
                } else { //unknown error
                    signUpResponse.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<Company?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                signUpResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")
            }
        })
    }

    fun signUpEmployer(input: SignUpItem, passwordConfirmation: String) {
        val build = RetrofitBuild.build().register(
            input.email,
            input.password,
            passwordConfirmation,
            input.phone,
            input.address,
            input.description,
            input.name,
            true,
            companyDetails.name,
            companyDetails.contact_number,
            companyDetails.reg_no,
            companyDetails.location,
            companyDetails.description,
        )

        build.enqueue(object : Callback<SignUpItem?> {
            override fun onResponse(call: Call<SignUpItem?>, response: Response<SignUpItem?>) {
                if (response.isSuccessful) {
                    signUpResponse.value = ResponseForUI(true, "")
                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ValidationErrorResponse::class.java
                    )
                    signUpResponse.value = ResponseForUI(false, error.message)
                    Log.d("login", "onResponse: $error")
                } else { //unknown error
                    signUpResponse.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<SignUpItem?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                signUpResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")
            }
        })
    }
}