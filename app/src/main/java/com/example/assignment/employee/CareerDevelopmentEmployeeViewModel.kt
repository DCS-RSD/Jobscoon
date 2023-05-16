package com.example.assignment.employee

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.*
import com.google.gson.Gson
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CareerDevelopmentEmployeeViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val getAllResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }


    val careerDevelopmentList: MutableLiveData<List<CareerDevelopmentItem>> by lazy {
        MutableLiveData<List<CareerDevelopmentItem>>()
    }

    private val _careerDevDetail = MutableLiveData<CareerDevelopmentItem>()
    val careerDevDetail: LiveData<CareerDevelopmentItem>
        get() = _careerDevDetail



    val careerDevId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun getData() {
        val build = RetrofitBuild.build().getCareerDevelopment(
            sharedPreferences.getString("Token", "")!!
        )

        build.enqueue(object : Callback<List<CareerDevelopmentItem>?> {
            override fun onResponse(
                call: Call<List<CareerDevelopmentItem>?>,
                response: Response<List<CareerDevelopmentItem>?>
            ) {
                if (response.isSuccessful) {

                    getAllResponse.value = ResponseForUI(true, "")
                    careerDevelopmentList.value = response.body()!!
                    Log.d("success", "onResponse: " + careerDevelopmentList.value)

                } else { //unknown error

                    getAllResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<List<CareerDevelopmentItem>?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                getAllResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")


            }


        })

    }


}