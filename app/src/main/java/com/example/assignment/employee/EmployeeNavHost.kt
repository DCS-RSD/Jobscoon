package com.example.assignment.employee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.assignment.R
import com.example.assignment.databinding.NavigationEmployeeBinding

class EmployeeNavHost : AppCompatActivity() {
    lateinit var binding: NavigationEmployeeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.navigation_employee)
        binding.bottomNavBar.setItemSelected(R.id.home)
        setBar()
        val navController = Navigation.findNavController(this, R.id.navigation_host_employee)
        visibilityNavElements(navController)

    }

    private fun setBar() {

        binding.bottomNavBar.setOnItemSelectedListener {
            when (it) {
                R.id.home -> {
                    replaceFragment(FindJobsEmployeeFragment())
                    binding.navName.text = getString(R.string.nav_title_home)
                }
                R.id.profile -> {
                    replaceFragment(ProfileEmployeeFragment())
                    binding.navName.text = getString(R.string.nav_title_profile)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navigation_host_employee, fragment)
            .commit()
    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.jobDetailsEmployeeFragment -> {
                    binding.bottomNavBar?.visibility = View.GONE
                    binding.toolbar?.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavBar?.visibility = View.VISIBLE
                    binding.toolbar?.visibility = View.VISIBLE
                }
            }
        }
    }

}