package com.example.assignment.auth

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.ValidationErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)

    val loginResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val isEmployer: MutableLiveData<Int> = MutableLiveData()

    fun submitLogin(email: String, password: String) {

        val build = RetrofitBuild.build()
            .login(email, password)

        build.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {

                    loginResponse.value = ResponseForUI(true, "")
                    isEmployer.value = response.body()!!.user.is_employer!!
                    val editor = sharedPreferences.edit()
                    editor.putString("Token", "Bearer " + response.body()!!.token)
                    editor.putString("Id", response.body()!!.user.id.toString())
                    editor.putString("IsEmployer", response.body()!!.user.is_employer.toString())
                    editor.apply()

                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ValidationErrorResponse::class.java
                    )

                    loginResponse.value = ResponseForUI(false, error.message)


                    Log.d("login", "onResponse: $error")

                } else if (response.code() == 401) { //wrong email or password

                    loginResponse.value = ResponseForUI(false, "Invalid Email or Password")

                } else { //unknown error

                    loginResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                loginResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")


            }
        })
    }


}