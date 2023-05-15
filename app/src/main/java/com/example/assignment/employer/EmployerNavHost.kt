package com.example.assignment.employer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.assignment.R
import com.example.assignment.databinding.NavigationEmployeeBinding
import com.example.assignment.databinding.NavigationEmployerBinding

class EmployerNavHost : AppCompatActivity() {
    lateinit var binding: NavigationEmployerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.navigation_employer)

        val navController = Navigation.findNavController(this, R.id.navigation_host_employer)
        binding.bottomNavBar.setupWithNavController(navController)
        visibilityNavElements(navController)

    }

    private fun visibilityNavElements(navController: NavController) {

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.postJobEmployerFragment,
                R.id.jobDetailsEmployerFragment,
                R.id.applicantListEmployerFragment,
                R.id.editJobFragment,
                R.id.editProfileEmployerFragment,
                R.id.editProfileEmployeeFragment,
                R.id.changePasswordFragment2,
                R.id.careerDevelopmentDetailsEmployerFragment,
                R.id.addCareerDevelopmentEmployerFragment,
                R.id.editCareerDevelopmentEmployerFragment,
                R.id.applicantCareerDevelopmentFragment,
                R.id.scheduleInterviewEmployerFragment -> {
                    binding.bottomNavBar?.visibility = View.GONE
                    binding.toolbar?.visibility = View.GONE
                    binding.nav?.visibility = View.GONE
                    binding.borderShadow?.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavBar?.visibility = View.VISIBLE
                    binding.toolbar?.visibility = View.VISIBLE
                    binding.nav.visibility = View.VISIBLE
                    binding.borderShadow.visibility = View.VISIBLE
                }
            }
        }


    }

}