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

class ApplicantListEmployerViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!
    var id=0

    val acceptResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val rejectResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    fun accept(applicationId: Int) {
        val build = RetrofitBuild.build().acceptApplication(token, applicationId)
        build.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    if(acceptResponse.value != ResponseForUI(true,"")) {
                        acceptResponse.postValue(ResponseForUI(true, ""))
                    }
                } else {
                    acceptResponse.postValue(ResponseForUI(false, "Something Went Wrong"))
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                acceptResponse.postValue(
                    ResponseForUI(
                        false,
                        "Something Went Wrong. Kindly check your connection"
                    )
                )
            }
        })
    }

    fun decline(applicationId: Int) {
        val build = RetrofitBuild.build().declineApplication(token, applicationId)
        build.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    if(rejectResponse.value != ResponseForUI(true,"")) {
                        rejectResponse.postValue(ResponseForUI(true, ""))
                    }
                } else {
                    rejectResponse.postValue(ResponseForUI(false, "Something Went Wrong"))
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                rejectResponse.postValue(
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")
                )

            }
        })
    }
}