package com.example.assignment.employee

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
import com.example.assignment.databinding.FragmentFindJobsEmployeeBinding
import com.example.assignment.databinding.FragmentInterviewEmployeeBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.employee.recycleviews.JobInterviewRecyclerAdapter
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter

class InterviewEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = InterviewEmployeeFragment()
    }

    private lateinit var binding: FragmentInterviewEmployeeBinding
    private lateinit var manager: RecyclerView.LayoutManager
    val sharedViewModel: InterviewEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_interview_employee,
            container,
            false
        )
        manager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.getData()


        sharedViewModel.jobInterviewList.observe(viewLifecycleOwner, Observer {
            binding.interviewEmployeeRecycleView.apply {
                adapter = JobInterviewRecyclerAdapter(sharedViewModel, requireContext(), it)
                layoutManager = manager
            }

            Log.d("acticity", "onActivityCreated: "+it)
        })

        binding.interviewEmployeeRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.interviewEmployeeRefresh.isRefreshing = false
        }

    }


}