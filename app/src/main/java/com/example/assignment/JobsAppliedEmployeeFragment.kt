package com.example.assignment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.assignment.databinding.FragmentJobsAppliedEmployeeBinding

class JobsAppliedEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentJobsAppliedEmployeeBinding

    companion object {
        fun newInstance() = JobsAppliedEmployeeFragment()
    }

    private lateinit var viewModel: JobsAppliedEmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentJobsAppliedEmployeeBinding>(inflater, R.layout.fragment_jobs_applied_employee, container, false)
        binding.iconArrowback.setOnClickListener{
                view : View -> view.findNavController().navigate(R.id.action_jobsAppliedEmployeeFragment_to_jobDetailsEmployeeFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JobsAppliedEmployeeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}