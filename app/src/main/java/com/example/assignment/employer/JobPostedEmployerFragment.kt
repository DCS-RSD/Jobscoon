package com.example.assignment.employer

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.EditProfileEmployeeViewModel
import com.example.assignment.R
import com.example.assignment.auth.AuthActivity
import com.example.assignment.databinding.FragmentJobPostedEmployerBinding
import com.example.assignment.employee.FindJobsEmployeeViewModel
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter
import com.example.assignment.employer.recycleviews.JobPostEmployerRecyclerAdapter

class JobPostedEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = JobPostedEmployerFragment()
    }

    private lateinit var binding: FragmentJobPostedEmployerBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private val sharedViewModel: JobPostedEmployerViewModel by activityViewModels()
    private var scrollPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_job_posted_employer,
            container,
            false
        )


        manager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(JobPostedEmployerViewModel::class.java)

        sharedViewModel.getData()

        sharedViewModel.jobPostList.observe(viewLifecycleOwner, Observer {
            binding.jobPostRecycleView.apply {
                adapter = JobPostEmployerRecyclerAdapter(sharedViewModel, it)
                layoutManager = manager
            }

            Log.d("acticity", "onActivityCreated: " + it)
        })

        //navigate to form
        binding.floatingActionButton.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_jobPostedEmployerFragment_to_postJobEmployerFragment)
        }

        binding.jobPostRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.jobPostRefresh.isRefreshing = false
        }

        //check api response
        sharedViewModel.getAllResponse.observe(viewLifecycleOwner, Observer { response ->
            if (!response.success) {
                sharedViewModel.isExpired.observe(viewLifecycleOwner, Observer {
                    if (it) {
                        //dialog
                        val intent = Intent(requireActivity(), AuthActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), response.errorMsg, Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        })

    }

    //scroll remain
//    override fun onPause() {
//        super.onPause()
//        scrollPosition = (manager as LinearLayoutManager).findFirstVisibleItemPosition()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        (manager as LinearLayoutManager).scrollToPosition(scrollPosition)
//    }
}