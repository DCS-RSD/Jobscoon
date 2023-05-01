package com.example.assignment.employee

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindJobsEmployeeViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var currentUser: User
    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)

    fun autoLogin() {
        val build = RetrofitBuild.build().autoLogin(
            sharedPreferences.getString("Token", "")!!,
            sharedPreferences.getString("Id", "")!!
        )



        build.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.isSuccessful) {
                    currentUser = response.body()!!
                } else {
                    //
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {

                Log.i("check", "onFailure: no")
            }
        })
    }
}