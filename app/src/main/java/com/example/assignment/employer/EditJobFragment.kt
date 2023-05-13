package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentEditJobBinding
import com.example.assignment.databinding.FragmentEditProfileEmployerBinding
import com.example.assignment.dataclass.Company
import com.example.assignment.dataclass.JobPostItem

class EditJobFragment : Fragment() {

    companion object {
        fun newInstance() = EditJobFragment()
    }

    private lateinit var viewModel: JobPostedEmployerViewModel
    private lateinit var binding: FragmentEditJobBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_job,
            container,
            false
        )

        binding.iconArrowback.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JobPostedEmployerViewModel::class.java)
        viewModel.showJobPost()
        viewModel.jobPostDetail.observe(viewLifecycleOwner, Observer {
            try {
                binding.jobPostItem = it
            } catch (e: Exception) {
                Log.e("exce", e.toString())
            }

        })


        binding.confirmJobBtn.setOnClickListener {
            viewModel.updateJobDetails(
                JobPostItem(
                    binding.editJobTitle.text.toString(),
                    binding.editJobType.text.toString(),
                    binding.editShiftFrom.text.toString(),
                    binding.editShiftTo.text.toString(),
                    binding.editSalaryStart.text.toString().toInt(),
                    binding.editSalaryEnd.text.toString().toInt(),
                    binding.editJobDescription.text.toString()
                )
            )
        }
        viewModel.postResponse.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                Toast.makeText(
                    requireContext(),
                    "Job Details Updated Successfully!",
                    Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    it.errorMsg,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}