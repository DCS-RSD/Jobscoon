package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<Button>(R.id.employer_login_btn)
        loginBtn.setOnClickListener {
            startActivity(Intent(this,CompanyProfileActivity::class.java))
        }
    }

    fun signUp(v:View){
        val intent = Intent(this, EmployerSignUpActivity::class.java)
        startActivity(intent)
    }
}