package com.example.assignment.employee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.FragmentInterviewHistoryBinding
import com.example.assignment.databinding.FragmentInterviewHistoryEmployeeBinding
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.employee.recycleviews.InterviewEmployeeHistoryRecyclerAdapter
import com.example.assignment.employer.recycleviews.InterviewEmployerHistoryRecyclerAdapter
import com.example.assignment.employer.recycleviews.InterviewEmployerRecyclerAdapter

class InterviewHistoryEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = InterviewHistoryEmployeeFragment()
    }

    private val sharedViewModel: InterviewEmployeeViewModel by activityViewModels()
    private lateinit var binding: FragmentInterviewHistoryEmployeeBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: InterviewEmployeeHistoryRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_interview_history_employee, container, false
        )

        manager = LinearLayoutManager(requireContext())
        recycleViewAdapter = InterviewEmployeeHistoryRecyclerAdapter(sharedViewModel,requireContext())
        binding.interviewHistoryRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.interviewHistoryList.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            if(it.isEmpty()){
                binding.textNoRecord.visibility = View.VISIBLE
                binding.interviewHistoryRecycleView.visibility = View.GONE
            }else{

                var newInterviewItem = ArrayList<JobInterviewItem>()

                for (item in it) {
                    if (item.job_post != null) {
                        newInterviewItem.add(item)
                    }
                }
                binding.textNoRecord.visibility = View.GONE
                binding.interviewHistoryRecycleView.visibility = View.VISIBLE
                recycleViewAdapter.setItem(newInterviewItem)
                binding.interviewHistoryRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        })

        binding.interviewHistoryRefresh.setOnRefreshListener {
            sharedViewModel.getHistoryData()
            sharedViewModel.getData()
            binding.interviewHistoryRefresh.isRefreshing =false
        }

        sharedViewModel.getHistoryResponse.observe(viewLifecycleOwner, Observer {
            if (!it.success){
                Toast.makeText(requireContext(),it.errorMsg, Toast.LENGTH_LONG).show()
            }
        })
    }

}