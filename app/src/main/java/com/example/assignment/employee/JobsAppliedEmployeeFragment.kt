package com.example.assignment.employee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.auth.SignUpViewModel
import com.example.assignment.databinding.FragmentJobsAppliedEmployeeBinding
import com.example.assignment.employee.recycleviews.JobAppliedRecyclerAdapter
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter

class JobsAppliedEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = JobsAppliedEmployeeFragment()
    }

    private lateinit var binding: FragmentJobsAppliedEmployeeBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var viewModel : JobsAppliedEmployeeViewModel
    val sharedViewModel: FindJobsEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jobs_applied_employee,
            container,
            false
        )
        manager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JobsAppliedEmployeeViewModel::class.java)


        viewModel.getData()
        viewModel.getJobApplicationData()


        viewModel.jobApplicationList.observe(viewLifecycleOwner, Observer { jobApplicationList ->

            viewModel.jobInterviewList.observe(viewLifecycleOwner, Observer { jobInterviewList ->

                        binding.jobAppliedRecycleView.apply {
                            adapter = JobAppliedRecyclerAdapter(sharedViewModel,jobApplicationList, jobInterviewList)
                            layoutManager = manager
                        }

                        Log.d("acticity", "onActivityCreated: " + jobApplicationList + jobInterviewList)

                    })
            })

        binding.jobAppliedRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.jobAppliedRefresh.isRefreshing = false
        }

    }

}