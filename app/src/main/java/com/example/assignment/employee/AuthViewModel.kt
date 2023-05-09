package com.example.assignment.employee

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.auth.LoginResponse
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(application: Application) :AndroidViewModel(application) {

    val responseUI: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val currentUser: MutableLiveData<User> by lazy{
        MutableLiveData<User>()
    }

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)

    fun getProfile() {
        val build = RetrofitBuild.build().myProfile(
            sharedPreferences.getString("Token", "")!!,
        )

        currentUser.value=null

        build.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.isSuccessful) {
                    currentUser.value = response.body()!!
                } else {
                    //dialog prompt to ask user login again
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {

                Log.i("check", "onFailure: no")
            }
        })
    }
    fun logout(token: String) {
        val build = RetrofitBuild.build().logout(token)
        build.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    responseUI.value = ResponseForUI(true, "")
                } else {
                    responseUI.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                responseUI.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })
    }
}