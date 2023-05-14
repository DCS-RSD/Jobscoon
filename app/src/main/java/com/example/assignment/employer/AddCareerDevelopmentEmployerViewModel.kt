package com.example.assignment

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCareerDevelopmentEmployerViewModel(application: Application) : AndroidViewModel(application) {
    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!
    val validationResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }



    fun createAddCareer(careerDevItem: CareerDevelopmentItem) {

        val build = RetrofitBuild.build().storeCareerDev(
            token,
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


}