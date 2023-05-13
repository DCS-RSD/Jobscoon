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

class JobDetailsEmployerViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!

    val jobPostDetail: MutableLiveData<JobPostItem> by lazy {
        MutableLiveData<JobPostItem>()
    }

    val showResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val deleteResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    fun showJobPost(id: Int) {
        val build = RetrofitBuild.build().showJobPost(
            token, id
        )

        build.enqueue(object : Callback<JobPostItem> {
            override fun onResponse(
                call: Call<JobPostItem>,
                response: Response<JobPostItem>
            ) {
                if (response.isSuccessful) {

                    showResponse.value = ResponseForUI(true, "")
                    jobPostDetail.value = response.body()!!
                    Log.d("success", "onResponse: " + jobPostDetail.value)

                } else { //unknown error

                    showResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<JobPostItem>, t: Throwable) {
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