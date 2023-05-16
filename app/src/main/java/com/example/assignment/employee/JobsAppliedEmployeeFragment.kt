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
    private val viewModel : JobsAppliedEmployeeViewModel by activityViewModels()
    private val sharedViewModel: FindJobsEmployeeViewModel by activityViewModels()

    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: JobAppliedRecyclerAdapter

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
        recycleViewAdapter = JobAppliedRecyclerAdapter(sharedViewModel)
        binding.jobAppliedRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getData()
        viewModel.getInterviewData()

        viewModel.jobApplicationList.observe(viewLifecycleOwner, Observer { jobApplicationList ->

            viewModel.jobInterviewList.observe(viewLifecycleOwner, Observer { jobInterviewList ->
                binding.loadingIcon.visibility = View.GONE
                if(jobApplicationList.isEmpty()){
                    binding.textNoRecord.visibility = View.VISIBLE
                    binding.jobAppliedRecycleView.visibility = View.INVISIBLE
                }else{
                    binding.textNoRecord.visibility = View.GONE
                    binding.jobAppliedRecycleView.visibility = View.VISIBLE

                    val sortedJobApplicationList = jobApplicationList.sortedBy { item ->
                        when (item.status) {
                            "accept" -> 0
                            "pending" -> 1
                            "declined" -> 2
                            else -> 3 // Handle other statuses if needed
                        }
                    }


                    recycleViewAdapter.setItem(sortedJobApplicationList, jobInterviewList)
                    binding.jobAppliedRecycleView.apply {
                        adapter?.notifyDataSetChanged()
                    }

                }
            })
        })

        binding.jobAppliedRefresh.setOnRefreshListener {
            viewModel.getData()
            viewModel.getInterviewData()
            binding.jobAppliedRefresh.isRefreshing = false
        }

    }

}