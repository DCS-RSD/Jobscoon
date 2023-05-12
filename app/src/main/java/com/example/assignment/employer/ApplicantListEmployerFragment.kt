package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.auth.SignUpEmployerViewModel
import com.example.assignment.databinding.FragmentApplicantListEmployerBinding
import com.example.assignment.databinding.FragmentFindJobsEmployeeBinding
import com.example.assignment.databinding.FragmentInterviewEmployeeBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.employee.recycleviews.ApplicantListEmployerRecyclerAdapter
import com.example.assignment.employee.recycleviews.JobInterviewRecyclerAdapter
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter

class ApplicantListEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = ApplicantListEmployerFragment()
    }

    private lateinit var binding: FragmentApplicantListEmployerBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private val sharedViewModel: JobPostedEmployerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_applicant_list_employer,
            container,
            false
        )
        manager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.getApplicantData()

        sharedViewModel.applicantList.observe(viewLifecycleOwner, Observer {
            binding.applicantListRecycleView.apply {
                adapter = ApplicantListEmployerRecyclerAdapter(sharedViewModel, it)
                layoutManager = manager
            }

            Log.d("applicant", "onActivityCreated: "+it)
        })

        binding.applicantListRefresh.setOnRefreshListener {
            sharedViewModel.getApplicantData()
            binding.applicantListRefresh.isRefreshing = false
        }

    }


}