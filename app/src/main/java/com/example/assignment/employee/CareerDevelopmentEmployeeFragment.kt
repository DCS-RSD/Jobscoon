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
import com.example.assignment.employee.recycleviews.CareerDevelopmentEmployeeRecyclerAdapter
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter

class CareerDevelopmentEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = CareerDevelopmentEmployeeFragment()
    }

    private lateinit var binding: FragmentCareerDevelopmentEmployeeBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: CareerDevelopmentEmployeeRecyclerAdapter
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

        recycleViewAdapter = CareerDevelopmentEmployeeRecyclerAdapter(sharedViewModel)
        binding.careerDevelopmentEmployeeRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.getData()

        sharedViewModel.careerDevelopmentList.observe(viewLifecycleOwner, Observer {


            binding.careerDevelopmentEmployeeRecycleView.visibility = View.VISIBLE
            binding.loadingIcon.visibility = View.GONE
            recycleViewAdapter.setItem(it)
            binding.careerDevelopmentEmployeeRecycleView.apply {
                adapter?.notifyDataSetChanged()
            }


            Log.d("acticity", "onActivityCreated: "+it)
        })

        binding.careerDevelopmentEmployeeRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.careerDevelopmentEmployeeRefresh.isRefreshing = false
        }

sharedViewModel.navigating.observe(viewLifecycleOwner, Observer {
    if (it){
        binding.careerDevelopmentEmployeeRecycleView.visibility = View.INVISIBLE
    }
})


    }

}