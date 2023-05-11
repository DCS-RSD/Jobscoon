package com.example.assignment.employer

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.CustomDialogBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployerBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter

class JobDetailsEmployerFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailsEmployerBinding

    companion object {
        fun newInstance() = JobDetailsEmployerFragment()
    }

    val sharedViewModel: JobPostedEmployerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentJobDetailsEmployerBinding>(
            inflater,
            R.layout.fragment_job_details_employer, container, false
        )

        val dialog = Dialog(requireContext())
        binding.applyButton.setOnClickListener {

        }


        binding.imageView.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }



        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.showJobPost()
        sharedViewModel.jobPostDetail.observe(viewLifecycleOwner, Observer {

            try {
                binding.jobDetailsEmployerFragment.apply {
                    binding.jobPostItem = it
                }



            } catch (e: Exception) {
            }


        })

        binding.jobDetailsRefresh.setOnRefreshListener {
            sharedViewModel.showJobPost()
            binding.jobDetailsRefresh.isRefreshing = false
        }
    }


}
