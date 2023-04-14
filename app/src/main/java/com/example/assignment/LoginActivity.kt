package com.example.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.assignment.api.LoginErrorResponse
import com.example.assignment.api.LoginResponse
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.api.Route
import com.example.assignment.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, EmployerSignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {

        val build = RetrofitBuild.build()
            .login(
                binding.loginEmail.text.toString(),
                binding.loginPassword.text.toString(),
            )

        build.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "login success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "login fail", Toast.LENGTH_SHORT).show()
                    Log.d("login", "onResponse: " + response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.d("fail", "onFailure: "+t.message)

            }
        })
    }

}