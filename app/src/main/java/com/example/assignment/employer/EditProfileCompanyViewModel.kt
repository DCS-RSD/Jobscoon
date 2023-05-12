package com.example.assignment.employer

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.Company
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.ValidationErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileCompanyViewModel(application: Application) : AndroidViewModel(application) {
    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!

    val companyDetails: MutableLiveData<Company> by lazy {
        MutableLiveData<Company>()
    }

    val getResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val postResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    fun getMyCompanyProfile() {
        val build = RetrofitBuild.build().myCompanyProfile(token)
        build.enqueue(object : Callback<Company?> {
            override fun onResponse(call: Call<Company?>, response: Response<Company?>) {
                if (response.isSuccessful) {
                    companyDetails.value = response.body()
//                    Log.d("success", "onResponse: "+response.body())
                } else {
                    getResponse.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<Company?>, t: Throwable) {
                getResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")
            }
        })
    }

    fun updateMyCompanyProfile(company: Company) {
        val build = RetrofitBuild.build().updateCompanyProfile(
            token,
            company.name,
            company.contact_number,
            company.email,
            company.reg_no,
            company.location,
            company.description
        )

        build.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    postResponse.value = ResponseForUI(true, "")
                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ValidationErrorResponse::class.java
                    )
                    postResponse.value = ResponseForUI(false, error.message)
                } else { //unknown error
                    postResponse.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                postResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })
    }
}