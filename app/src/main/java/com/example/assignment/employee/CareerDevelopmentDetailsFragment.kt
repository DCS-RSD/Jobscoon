package com.example.assignment.employee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.CareerDevelopmentDetailsViewModel
import com.example.assignment.R

class CareerDevelopmentDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CareerDevelopmentDetailsFragment()
    }

    private lateinit var viewModel: CareerDevelopmentDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_career_development_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CareerDevelopmentDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}