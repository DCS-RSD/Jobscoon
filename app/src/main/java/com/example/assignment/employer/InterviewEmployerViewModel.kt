package com.example.assignment.employer

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.CareerDevelopmentItem
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.dataclass.ResponseForUI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InterviewEmployerViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!
    var id = 0

    val getResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val deleteResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val jobInterviewList: MutableLiveData<List<JobInterviewItem>> by lazy {
        MutableLiveData<List<JobInterviewItem>>()
    }

    fun getData() {
        val build = RetrofitBuild.build().getJobInterview(token)
        build.enqueue(object : Callback<List<JobInterviewItem>?> {
            override fun onResponse(
                call: Call<List<JobInterviewItem>?>,
                response: Response<List<JobInterviewItem>?>
            ) {
                if (response.isSuccessful) {
                    jobInterviewList.value = response.body()
                    getResponse.postValue(ResponseForUI(true, ""))
                } else {
                    getResponse.postValue(ResponseForUI(false, "Something Went Wrong"))
                }
            }

            override fun onFailure(call: Call<List<JobInterviewItem>?>, t: Throwable) {
                getResponse.postValue(
                    ResponseForUI(
                        false,
                        "Something Went Wrong. Kindly Check Your Connection"
                    )
                )
            }
        })

    }

    fun delete() {
        val build = RetrofitBuild.build().deleteInterview(token, id)
        build.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    deleteResponse.postValue(ResponseForUI(true, ""))
                } else {
                    deleteResponse.postValue(ResponseForUI(false, "Something Went Wrong"))

                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {

                deleteResponse.postValue(
                    ResponseForUI(
                        false,
                        "Something Went Wrong. Kindly Check Your Connection"
                    )
                )

            }
        })
    }
}