package com.example.assignment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.recycleviews.JobPostRecyclerAdapter

class JobsPostedEmployerFragment : Fragment() {

    lateinit var jobAdapter: JobPostRecyclerAdapter

    companion object {
        fun newInstance() = JobsPostedEmployerFragment()
    }

    private lateinit var viewModel: JobsPostedEmployerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_jobs_posted_employer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JobsPostedEmployerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}