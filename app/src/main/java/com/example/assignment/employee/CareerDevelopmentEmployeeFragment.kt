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
import com.example.assignment.databinding.FragmentCareerDevelopmentEmployeeBinding
import com.example.assignment.databinding.FragmentFindJobsEmployeeBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.recycleviews.CareerDevelopmentEmployeeRecyclerAdapter
import com.example.assignment.recycleviews.JobPostRecyclerAdapter

class CareerDevelopmentEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = CareerDevelopmentEmployeeFragment()
    }

    private lateinit var binding: FragmentCareerDevelopmentEmployeeBinding
    private lateinit var manager: RecyclerView.LayoutManager
    val sharedViewModel: CareerDevelopmentEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_career_development_employee,
            container,
            false
        )
        manager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.getData()

        sharedViewModel.careerDevelopmentList.observe(viewLifecycleOwner, Observer {
            binding.careerDevelopmentEmployeeRecycleView.apply {
                adapter = CareerDevelopmentEmployeeRecyclerAdapter(sharedViewModel, it)
                layoutManager = manager
            }

            Log.d("acticity", "onActivityCreated: "+it)
        })

        binding.careerDevelopmentEmployeeRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.careerDevelopmentEmployeeRefresh.isRefreshing = false
        }


    }

}