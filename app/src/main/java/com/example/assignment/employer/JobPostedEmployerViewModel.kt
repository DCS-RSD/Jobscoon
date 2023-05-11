package com.example.assignment.employer

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.ResponseForUI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobPostedEmployerViewModel(application: Application) : AndroidViewModel(application) {
    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!

    val jobPostList: MutableLiveData<List<JobPostItem>> by lazy {
        MutableLiveData<List<JobPostItem>>()
    }

    val getAllResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val isExpired: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val jobPostId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    fun getData() {
        val build = RetrofitBuild.build().getJobPost(token)

        build.enqueue(object : Callback<List<JobPostItem>?> {
            override fun onResponse(
                call: Call<List<JobPostItem>?>,
                response: Response<List<JobPostItem>?>
            ) {
                if (response.isSuccessful) {

                    getAllResponse.value = ResponseForUI(true, "")
                    jobPostList.value = response.body()!!
                    Log.d("success", "onResponse: " + jobPostList.value)

                } else if (response.code() == 401) { //unknown error, mostly 401 (unauthorized)

//                    println("wtf"+token)
                    sharedPreferences.edit().clear().apply() //clear token (401)
                    //session expired dialog
                    isExpired.value = true
                    getAllResponse.value = ResponseForUI(false, "Session Expired")

                } else {
                    getAllResponse.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<List<JobPostItem>?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)
                getAllResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")
            }
        })
    }
}