package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EmployerSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_sign_up)

        val btn = findViewById<Button>(R.id.sign_up_btn)
        btn.setOnClickListener{
            startActivity(Intent(this,EmployerHomeActivity::class.java))
        }

    }
}