package com.example.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.assignment.dataclass.ValidationErrorResponse
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation

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
            //val intent = Intent(this, EmployerSignUpActivity::class.java)
            //startActivity(intent)
        }
    }





}