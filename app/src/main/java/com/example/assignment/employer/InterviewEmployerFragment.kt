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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.FragmentInterviewEmployerBinding
import com.example.assignment.employer.recycleviews.InterviewEmployerRecyclerAdapter


class InterviewEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = InterviewEmployerFragment()
    }

    private val sharedViewModel: InterviewEmployerViewModel by activityViewModels()
    private lateinit var binding: FragmentInterviewEmployerBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: InterviewEmployerRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_interview_employer,
            container,
            false
        )

        manager = LinearLayoutManager(requireContext())
        recycleViewAdapter = InterviewEmployerRecyclerAdapter(sharedViewModel,requireContext())
        binding.interviewEmployerRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(InterviewEmployerViewModel::class.java)
        sharedViewModel.getData()

        sharedViewModel.jobInterviewList.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            if(it.isEmpty()){
                binding.textNoRecord.visibility = View.VISIBLE
                binding.interviewEmployerRecycleView.visibility = View.GONE
            }else{
                binding.textNoRecord.visibility = View.GONE
                binding.interviewEmployerRecycleView.visibility = View.VISIBLE
                recycleViewAdapter.setItem(it)
                binding.interviewEmployerRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        })

        binding.interviewEmployerRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.interviewEmployerRefresh.isRefreshing = false
        }

        sharedViewModel.getResponse.observe(viewLifecycleOwner, Observer {
            if (!it.success){
                Toast.makeText(requireContext(),it.errorMsg,Toast.LENGTH_LONG).show()
            }
        })

        sharedViewModel.deleteResponse.observe(context as LifecycleOwner, Observer {
            try {
                if (it.success) {
                    sharedViewModel.getData()
                    Toast.makeText(context, "Interview Deleted Successfully", Toast.LENGTH_LONG)
                        .show()
                    sharedViewModel.deleteResponse.postValue(null) //reset
                } else {
                    Toast.makeText(context, it.errorMsg, Toast.LENGTH_LONG).show()
                    sharedViewModel.deleteResponse.postValue(null) //reset
                }
            }catch (e:Exception){}
        })

        sharedViewModel.navigating.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.loadingIcon.visibility=View.VISIBLE
                binding.interviewEmployerRecycleView.visibility=View.INVISIBLE
            }
        })
    }

}