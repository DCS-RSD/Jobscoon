package com.example.assignment.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.NavigationHostBinding

class EmployeeNavHost : AppCompatActivity() {
    lateinit var binding: NavigationHostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.navigation_host)

        setBar()

    }

    private fun setBar() {
        binding.bottomNavBar.setItemSelected(R.id.home)
//        binding.bottomNavBar.setOnClickListener {  }
    }
}