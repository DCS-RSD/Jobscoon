package com.example.assignment.employer

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.CareerDevelopmentItem
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.ResponseForUI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CareerDevelopmentDetailsEmployerViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!

    val careerDevDetail: MutableLiveData<CareerDevelopmentItem> by lazy {
        MutableLiveData<CareerDevelopmentItem>()
    }

    val showResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val deleteResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }


    fun showCareerDev(id: Int) {
        val build = RetrofitBuild.build().showCareer(
            token, id
        )

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

    fun deleteJobPost(id: Int) {
        val build = RetrofitBuild.build().deleteJobPost(token, id)
        build.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    deleteResponse.value = ResponseForUI(true, "")
                } else { //unknown error
                    deleteResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                deleteResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")
            }
        })
    }
}