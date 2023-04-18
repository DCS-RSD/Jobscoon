package com.example.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.example.assignment.auth.LoginErrorResponse
import com.example.assignment.auth.LoginResponse
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.databinding.ActivityLoginBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, NavigationHost::class.java)
            startActivity(intent)
            /*binding.loginContainer.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_login, FindJobsEmployeeFragment())
                .addToBackStack(null)
                .commit()

             */
            //submitLogin()
        }

        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this, EmployerSignUpActivity::class.java)
            startActivity(intent)
        }
    }



    private fun submitLogin() {

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
                    startActivity(Intent(this@LoginActivity, EmployerHomeActivity::class.java))

                    val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("Token","Bearer " + response.body()!!.token)
                    editor.apply()
                    finish()

                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        LoginErrorResponse::class.java
                    )

                    Toast.makeText(
                        applicationContext,
                        "Login Fail : " + error.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.d("login", "onResponse: " + error.errors)
                } else if (response.code() == 401) { //wrong email or password
                    Toast.makeText(
                        applicationContext,
                        "Invalid Email or Password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else { //unknown error
                    Toast.makeText(
                        applicationContext,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                Toast.makeText( //no connection
                    applicationContext,
                    "Something went wrong. Kindly check your connection",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


}