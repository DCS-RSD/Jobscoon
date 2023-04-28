package com.example.assignment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.assignment.databinding.FragmentInterviewEmployeeBinding

class InterviewEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentInterviewEmployeeBinding

    companion object {
        fun newInstance() = InterviewEmployeeFragment()
    }

    private lateinit var viewModel: InterviewEmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentInterviewEmployeeBinding>(inflater, R.layout.fragment_interview_employee, container, false)
        binding.iconArrowback.setOnClickListener {
            view : View -> view.findNavController().navigate(R.id.action_interviewEmployeeFragment_to_jobsAppliedEmployeeFragment)
        }
        binding.iconHome.setOnClickListener {
                view : View -> view.findNavController().navigate(R.id.action_interviewEmployeeFragment_to_findJobsEmployeeFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InterviewEmployeeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}