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
import androidx.lifecycle.get
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
import com.example.assignment.employee.recycleviews.JobAppliedRecyclerAdapter
import com.example.assignment.employee.recycleviews.JobInterviewRecyclerAdapter
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter
import com.example.assignment.employer.recycleviews.JobPostEmployerRecyclerAdapter

class ApplicantListEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = ApplicantListEmployerFragment()
    }

    private lateinit var binding: FragmentApplicantListEmployerBinding
    private val sharedViewModel: JobPostedEmployerViewModel by activityViewModels()
    private val viewModel: ApplicantListEmployerViewModel by activityViewModels()

    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: ApplicantListEmployerRecyclerAdapter

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
        recycleViewAdapter = ApplicantListEmployerRecyclerAdapter(viewModel,requireContext())
        binding.applicantListRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        binding.imageView.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = sharedViewModel.jobPostId.value!!

        viewModel.getApplicantData(id)

        viewModel.applicantList.observe(viewLifecycleOwner, Observer {
            binding.applicantListRecycleView.visibility = View.VISIBLE
            binding.loadingIcon.visibility = View.GONE
            if (it.isEmpty()) {
                binding.textNoRecord.visibility = View.VISIBLE
            } else {
                recycleViewAdapter.setItem(it)
                binding.applicantListRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        })

        binding.applicantListRefresh.setOnRefreshListener {
            viewModel.getApplicantData(id)
            binding.applicantListRefresh.isRefreshing = false
        }

    }


}