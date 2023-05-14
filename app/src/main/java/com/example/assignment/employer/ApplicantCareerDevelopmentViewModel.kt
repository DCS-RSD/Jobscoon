package com.example.assignment.employer

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplicantCareerDevelopmentViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!
    val getResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val acceptResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val rejectResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val applicantList: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>()
    }

    fun getApplicantData(id: Int) {
        val build = RetrofitBuild.build().getCareerApplicant(token, id)

        build.enqueue(object : Callback<List<User>?> {
            override fun onResponse(
                call: Call<List<User>?>,
                response: Response<List<User>?>
            ) {
                if (response.isSuccessful) {

                    getResponse.value = ResponseForUI(true, "")
                    applicantList.value = response.body()!!
                    Log.d("applicant", "onResponse: " + applicantList.value)

                } else { //unknown error

                    getResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<List<User>?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                getResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")


            }


        })

    }


}