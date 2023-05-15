package com.example.assignment.employer

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.dataclass.ResponseForUI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InterviewHistoryViewModel(application: Application) : AndroidViewModel(application) {
    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("Token", "")!!


}