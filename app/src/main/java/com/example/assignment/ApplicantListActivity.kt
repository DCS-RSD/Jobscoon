package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ApplicantListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_list)

        val acceptBtn = findViewById<Button>(R.id.accept)
        acceptBtn.setOnClickListener {
            startActivity(Intent(this,InterviewFormLayout::class.java))
        }
    }
}