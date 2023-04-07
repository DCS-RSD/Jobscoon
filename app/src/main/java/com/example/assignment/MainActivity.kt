package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val intent = Intent(this, JobPostFormActivity::class.java)
        startActivity(intent)

        val btn = findViewById<Button>(R.id.button2)
        btn.setOnClickListener{

        }


    }
}

