package com.example.assignment.employee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.assignment.ProfileEmployeeViewModel
import com.example.assignment.R
import com.example.assignment.databinding.FragmentProfileEmployeeBinding

class ProfileEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileEmployeeFragment()
    }

    val sharedViewModel: FindJobsEmployeeViewModel by activityViewModels()
    lateinit var binding: FragmentProfileEmployeeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_employee, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val user = sharedViewModel.currentUser
        binding.user = user
    }

}