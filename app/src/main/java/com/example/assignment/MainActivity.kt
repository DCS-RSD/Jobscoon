package com.example.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.assignment.auth.AuthActivity
import com.example.assignment.employee.EmployeeNavHost

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       //getSharedPreferences("User", Context.MODE_PRIVATE).edit().clear().apply()

        if (getSharedPreferences("User", Context.MODE_PRIVATE).getString("Token", "") != "") {
            startActivity(Intent(this, EmployeeNavHost::class.java))
        } else {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        finish()

//        Handler().postDelayed({
//
//        }, 2000)


    }
}

