package com.example.assignment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class EditCareerDevelopmentEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = EditCareerDevelopmentEmployerFragment()
    }

    private lateinit var viewModel: EditCareerDevelopmentEmployerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.fragment_edit_career_development_employer,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditCareerDevelopmentEmployerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}