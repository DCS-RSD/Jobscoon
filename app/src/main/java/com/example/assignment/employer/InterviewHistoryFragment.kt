package com.example.assignment.employer

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
import com.example.assignment.employer.recycleviews.InterviewEmployerHistoryRecyclerAdapter
import com.example.assignment.employer.recycleviews.InterviewEmployerRecyclerAdapter

class InterviewHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = InterviewHistoryFragment()
    }

    private val sharedViewModel: InterviewEmployerViewModel by activityViewModels()
    private lateinit var binding: FragmentInterviewHistoryBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: InterviewEmployerHistoryRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_interview_history, container, false
        )

        manager = LinearLayoutManager(requireContext())
        recycleViewAdapter = InterviewEmployerHistoryRecyclerAdapter(sharedViewModel,requireContext())
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
                binding.textNoRecord.visibility = View.GONE
                binding.interviewHistoryRecycleView.visibility = View.VISIBLE
                recycleViewAdapter.setItem(it)
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