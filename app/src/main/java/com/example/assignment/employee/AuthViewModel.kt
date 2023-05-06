package com.example.assignment.employee

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.auth.LoginResponse
import com.example.assignment.dataclass.ResponseForUI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    val responseUI: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
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