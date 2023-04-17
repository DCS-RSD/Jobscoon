package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EmployerSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_employer)

        val btn = findViewById<Button>(R.id.signUpS)
        btn.setOnClickListener{
            startActivity(Intent(this,EmployerHomeActivity::class.java))
        }

    }
}