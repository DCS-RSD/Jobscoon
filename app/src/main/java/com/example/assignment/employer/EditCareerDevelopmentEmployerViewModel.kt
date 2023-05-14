package com.example.assignment.employer

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.CareerDevelopmentItem
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.ValidationErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCareerDevelopmentEmployerViewModel(application: Application) : AndroidViewModel(application) {
    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!

    val validationResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }
    fun updateCareerDetails(careerDevItem: CareerDevelopmentItem, id: Int) {

        val build = RetrofitBuild.build().updateCareerDetails(
            token,
            id,
            careerDevItem.title!!,
            careerDevItem.date_start!!,
            careerDevItem.date_end!!,
            careerDevItem.start_time!!,
            careerDevItem.end_time!!,
            careerDevItem.type!!,
            careerDevItem.location!!,
            careerDevItem.link!!,
            careerDevItem.capacity!!,
            careerDevItem.description!!,
        )

        build.enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    validationResponse.value = ResponseForUI(true, "")
                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ValidationErrorResponse::class.java
                    )
                    validationResponse.value = ResponseForUI(false, error.message)
                    Log.d("login", "onResponse: $error")
                } else { //unknown error
                    validationResponse.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                validationResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })
    }

    val careerDevDetail : MutableLiveData<CareerDevelopmentItem> by lazy {
        MutableLiveData<CareerDevelopmentItem>()
    }

    val getResponse : MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    fun getCareerDevDetails(id: Int) {
        val build = RetrofitBuild.build().showCareer(
            token, id
        )

        build.enqueue(object : Callback<CareerDevelopmentItem> {
            override fun onResponse(
                call: Call<CareerDevelopmentItem>,
                response: Response<CareerDevelopmentItem>
            ) {
                if (response.isSuccessful) {

                    careerDevDetail.value = response.body()!!
                    Log.d("success", "onResponse: " + careerDevDetail.value)

                } else { //unknown error

                    getResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<CareerDevelopmentItem>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                getResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }
}