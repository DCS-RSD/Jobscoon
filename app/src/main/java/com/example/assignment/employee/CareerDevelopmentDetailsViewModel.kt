package com.example.assignment.employee

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.CareerDevelopmentItem
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.ValidationErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CareerDevelopmentDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!

    val careerDevDetail: MutableLiveData<CareerDevelopmentItem> by lazy {
        MutableLiveData<CareerDevelopmentItem>()
    }


    val applyResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val showResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }


    fun showCareerDev(id: Int) {
        val build = RetrofitBuild.build().showCareer(token, id)

        build.enqueue(object : Callback<CareerDevelopmentItem> {
            override fun onResponse(
                call: Call<CareerDevelopmentItem>,
                response: Response<CareerDevelopmentItem>
            ) {
                if (response.isSuccessful) {

                    showResponse.value = ResponseForUI(true, "")
                    careerDevDetail.value = response.body()!!
                    Log.d("success", "onResponse: " + careerDevDetail.value)

                } else { //unknown error

                    showResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<CareerDevelopmentItem>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                showResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }

    fun postJoinData(id : Int) {
        val build = RetrofitBuild.build().applyCareer(
            sharedPreferences.getString("Token", "")!!, id
        )

        build.enqueue(object : Callback<ValidationErrorResponse> {
            override fun onResponse(
                call: Call<ValidationErrorResponse>,
                response: Response<ValidationErrorResponse>
            ) {
                if (response.isSuccessful) {

                    applyResponse.value = ResponseForUI(true, "")
                    //jobApplicationItem.value = response.body()!!
                    //Log.d("success", "onResponse: " + jobApplicationItem.value)

                } else { //unknown error

                    applyResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<ValidationErrorResponse>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                applyResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }

    fun postUnjoinData(id : Int) {
        val build = RetrofitBuild.build().cancelCareer(
            sharedPreferences.getString("Token", "")!!, id
        )

        build.enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {

                    applyResponse.value = ResponseForUI(true, "")
                    //jobApplicationItem.value = response.body()!!
                    //Log.d("success", "onResponse: " + jobApplicationItem.value)

                } else { //unknown error

                    applyResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                applyResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }


}