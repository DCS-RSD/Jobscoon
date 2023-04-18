package com.example.assignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.assignment.databinding.NavigationHostBinding

class NavigationHost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<NavigationHostBinding>(this, R.layout.navigation_host)
        val navController = this.findNavController(R.id.navigation_host)
    }
}