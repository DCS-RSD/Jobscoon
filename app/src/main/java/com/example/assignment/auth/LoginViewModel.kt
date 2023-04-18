package com.example.assignment.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.EmployerHomeActivity
import com.example.assignment.api.RetrofitBuild
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class LoginResponseUI(
    var success : Boolean,
    var errorMsg : String
)

class LoginViewModel() : ViewModel() {


    val loginResponse: MutableLiveData<LoginResponseUI> by lazy {
        MutableLiveData<LoginResponseUI>()
    }


    fun submitLogin(email: String, password: String) {

        val build = RetrofitBuild.build()
            .login(email, password)

        build.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    loginResponse.value = LoginResponseUI(true,"")

                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        LoginErrorResponse::class.java
                    )

                    loginResponse.value = LoginResponseUI(false,error.message)


                    Log.d("login", "onResponse: " + error.errors)

                } else if (response.code() == 401) { //wrong email or password

                    loginResponse.value = LoginResponseUI(true,"Invalid Email or Password")

                } else { //unknown error

                    loginResponse.value = LoginResponseUI(true,"Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                loginResponse.value = LoginResponseUI(true,"Something Went Wrong. Kindly check your connection")


            }
        })
    }


}