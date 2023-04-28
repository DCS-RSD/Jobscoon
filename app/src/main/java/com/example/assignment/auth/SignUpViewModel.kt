package com.example.assignment.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.ValidationErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    val signUpResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    fun submitSignUp(input: SignUpItem, passwordConfirmation: String) {
        val build = RetrofitBuild.build().register(
            input.email,
            input.password,
            passwordConfirmation,
            input.phone,
            input.address,
            input.description,
            input.name,
        )
        build.enqueue(object : Callback<SignUpItem?> {
            override fun onResponse(call: Call<SignUpItem?>, response: Response<SignUpItem?>) {
                if (response.isSuccessful) {
                    signUpResponse.value = ResponseForUI(true, "")
                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ValidationErrorResponse::class.java
                    )
                    signUpResponse.value = ResponseForUI(false, error.message)
                    Log.d("login", "onResponse: $error")
                } else { //unknown error
                    signUpResponse.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<SignUpItem?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                signUpResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })
    }
}