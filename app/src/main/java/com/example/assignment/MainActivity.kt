package com.example.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.auth.AuthActivity
import com.example.assignment.employee.AuthViewModel
import com.example.assignment.employee.EmployeeNavHost
import com.example.assignment.employer.EmployerNavHost

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        var num = viewModel.getProfileSpecial()
        //getSharedPreferences("User", Context.MODE_PRIVATE).edit().clear().apply()


        if (getSharedPreferences("User", Context.MODE_PRIVATE).getString("Token", "") != "") {


            if(viewModel.getProfileSpecial() == 1) {
                startActivity(Intent(this, EmployerNavHost::class.java))
            }else {
                Log.d("success", "onResponse: " + viewModel.getProfileSpecial() + "main")
                startActivity(Intent(this, EmployeeNavHost::class.java))
            }

        } else {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        finish()

//        Handler().postDelayed({
//
//        }, 2000)


    }

}

