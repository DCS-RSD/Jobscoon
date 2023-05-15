package com.example.assignment.employer

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.dataclass.ResponseForUI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleInterviewEmployerViewModel(application: Application) : AndroidViewModel(application) {
    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!
    val validationResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    fun createInterview(id: Int, interviewItem: JobInterviewItem) {
        val build = RetrofitBuild.build().scheduleInterview(
            token, id,
            interviewItem.date,
            interviewItem.start_time,
            interviewItem.end_time,
            interviewItem.type,
            interviewItem.link,
            interviewItem.location,
            interviewItem.description,
        )

        build.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful){
                    validationResponse.value = ResponseForUI(true,"")
                }else   {
                    validationResponse.value = ResponseForUI(false,"Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                validationResponse.value = ResponseForUI(false,"Something Went Wrong. Kindly Check Your Connection")
            }
        })
    }


}