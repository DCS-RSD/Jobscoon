package com.example.assignment.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.assignment.R
import com.example.assignment.databinding.NavigationEmployeeBinding

class EmployeeNavHost : AppCompatActivity() {
    lateinit var binding: NavigationEmployeeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.navigation_employee)

        setBar()

    }

    private fun setBar() {
        binding.bottomNavBar.setItemSelected(R.id.home)
//        binding.bottomNavBar.setOnClickListener {  }
    }
}