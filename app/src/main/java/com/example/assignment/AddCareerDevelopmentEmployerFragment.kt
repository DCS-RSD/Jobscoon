package com.example.assignment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AddCareerDevelopmentEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = AddCareerDevelopmentEmployerFragment()
    }

    private lateinit var viewModel: AddCareerDevelopmentEmployerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_add_career_development_employer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddCareerDevelopmentEmployerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}