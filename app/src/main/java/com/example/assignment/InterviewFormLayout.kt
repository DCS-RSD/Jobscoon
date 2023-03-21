package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

//to be delete
class InterviewFormLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_form_layout)
        val typeSpinner = findViewById<Spinner>(R.id.type_spinner)
        val interviewType = arrayOf("Physical", "Virtual")

        ArrayAdapter.createFromResource(
            this,
            R.array.interview_type,
            android.R.layout.simple_spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeSpinner.adapter = adapter
            }

        val confirmBtn = findViewById<Button>(R.id.comfirm_interview_btn)
        confirmBtn.setOnClickListener {
            startActivity(Intent(this, UpcomingInterviewActivity::class.java))
        }
    }
}