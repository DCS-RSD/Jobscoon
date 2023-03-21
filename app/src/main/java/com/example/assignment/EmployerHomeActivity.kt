package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EmployerHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_home)

//        val card = findViewById<CardView>(R.id.jobCard)
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomActionBar)
//
//        card.setOnClickListener {
//            bottomNav.setVisibility(View.VISIBLE)
//        }

        val viewApplicant = findViewById<Button>(R.id.viewApplicant)
        viewApplicant.setOnClickListener {
            startActivity(Intent(this,ApplicantListActivity::class.java))
        }


    }
}