package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
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

    private lateinit var viewModel: EditJobViewModel
    val sharedViewModel: JobPostedEmployerViewModel by activityViewModels()
    private lateinit var binding: FragmentEditJobBinding
    val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val jobTypes = arrayOf("Full-time", "Part-time")
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

        binding.editShiftFrom.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                days
            )
        )
        binding.editShiftTo.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                days
            )
        )
        binding.editJobType.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                jobTypes
            )
        )

        binding.iconArrowback.setOnClickListener {
            it.findNavController().popBackStack()
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditJobViewModel::class.java)
        val id = sharedViewModel.jobPostId.value!!
        viewModel.getJobPostDetails(id)
        viewModel.jobPostDetail.observe(viewLifecycleOwner, Observer {
            binding.jobPostItem = it
            binding.editShiftFrom.setText(it.shift_start, false)
            binding.editShiftTo.setText(it.shift_end, false)
            binding.editJobType.setText(it.type,false)
        })


        binding.confirmJobBtn.setOnClickListener {


            var salaryUpper: Int? = null
            var salaryLower: Int? = null

            if (binding.editSalaryStart.text.toString() != "") {
                salaryUpper = binding.editSalaryStart.text.toString().toInt()
            }

            if (binding.editSalaryEnd.text.toString() != "") {
                salaryLower = binding.editSalaryEnd.text.toString().toInt()
            }

            viewModel.updateJobDetails(
                JobPostItem(
                    binding.editJobTitle.text.toString(),
                    binding.editJobType.text.toString(),
                    binding.editShiftFrom.text.toString(),
                    binding.editShiftTo.text.toString(),
                    salaryUpper,
                    salaryLower,
                    binding.editJobDescription.text.toString()
                ), id
            )
        }

        viewModel.validationResponse.observe(viewLifecycleOwner, Observer {
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