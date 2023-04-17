package com.example.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.core.os.HandlerCompat.postDelayed

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
//getSharedPreferences("User",Context.MODE_PRIVATE).edit().clear().apply()
        Handler().postDelayed({
            if (getSharedPreferences("User", Context.MODE_PRIVATE).getString("Token", "") != "") {
                startActivity(Intent(this, EmployerHomeActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        },2000)



    }
}

