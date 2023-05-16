package com.example.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.auth.AuthActivity
import com.example.assignment.employee.EmployeeNavHost
import com.example.assignment.employer.EmployerNavHost

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        val sharedPreferences =getSharedPreferences("User", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("Token", "")!!
        val isEmployer = sharedPreferences.getString("IsEmployer", "")!!
        //getSharedPreferences("User", Context.MODE_PRIVATE).edit().clear().apply()

        val flag = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        if (token != "") {
            viewModel.checkUserType()


                if (isEmployer == "1") {
                    Log.d("EMPLOYER", "onCreate: ")
                    startActivity(Intent(this, EmployerNavHost::class.java).apply {
                        flags = flag
                    })
                } else {
                    Log.d("EMPLOYEE", "onCreate: ")
                    startActivity(Intent(this, EmployeeNavHost::class.java).apply {
                        flags = flag
                    })
                }
        } else {
            startActivity(Intent(this, AuthActivity::class.java).apply {
                flags = flag
            })
        }

        //if user token not exist in database
        viewModel.unauthorized.observe(this, Observer {
            if (it){
                sharedPreferences.edit().clear().apply()
                startActivity(Intent(this, AuthActivity::class.java).apply {
                    flags = flag
                })
            }
        })


    }

}

