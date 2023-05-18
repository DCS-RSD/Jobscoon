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
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.employee.recycleviews.JobInterviewRecyclerAdapter
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter
import com.example.assignment.employer.recycleviews.InterviewEmployerRecyclerAdapter

class InterviewEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = InterviewEmployeeFragment()
    }

    private lateinit var binding: FragmentInterviewEmployeeBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: JobInterviewRecyclerAdapter
    private val sharedViewModel: InterviewEmployeeViewModel by activityViewModels()

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

        recycleViewAdapter = JobInterviewRecyclerAdapter(sharedViewModel,requireContext())
        binding.interviewEmployeeRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.getData()


        sharedViewModel.jobInterviewList.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            if(it.isEmpty()){
                binding.textNoRecord.visibility = View.VISIBLE
                binding.interviewEmployeeRecycleView.visibility = View.GONE
            }else{
                var newInterviewItem = ArrayList<JobInterviewItem>()

                for (item in it) {
                    if (item.job_post != null) {
                        newInterviewItem.add(item)
                    }
                }


                val sortedJobInterviewList = newInterviewItem.sortedBy { item ->
                    when (item.status) {
                        "accept" -> 0
                        "pending" -> 1
                        "declined" -> 2
                        else -> 3 // Handle other statuses if needed
                    }
                }
                binding.textNoRecord.visibility = View.GONE
                binding.interviewEmployeeRecycleView.visibility = View.VISIBLE
                recycleViewAdapter.setItem(sortedJobInterviewList)
                binding.interviewEmployeeRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }

            Log.d("acticity", "onActivityCreated: "+it)
        })

        binding.interviewEmployeeRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.interviewEmployeeRefresh.isRefreshing = false
        }

        sharedViewModel.getResponse.observe(viewLifecycleOwner, Observer {
            if (!it.success){
                Toast.makeText(requireContext(),it.errorMsg,Toast.LENGTH_LONG).show()
            }
        })

        sharedViewModel.navigating.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.loadingIcon.visibility=View.VISIBLE
                binding.interviewEmployeeRecycleView.visibility = View.INVISIBLE
            }
        })

    }


}