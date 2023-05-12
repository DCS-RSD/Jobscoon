package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.assignment.EditProfileEmployeeViewModel
import com.example.assignment.PostJobEmployerViewModel
import com.example.assignment.R
import com.example.assignment.databinding.FragmentPostJobEmployerBinding
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.User

class PostJobEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = PostJobEmployerFragment()
    }

    private lateinit var viewModel: PostJobEmployerViewModel
    private lateinit var binding: FragmentPostJobEmployerBinding
    val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val jobTypes = arrayOf("Full-time", "Part-time")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentPostJobEmployerBinding>(
            inflater,
            R.layout.fragment_post_job_employer, container, false
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

        binding.iconArrowback.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(PostJobEmployerViewModel::class.java)


        binding.confirmJobBtn.setOnClickListener {

            var salaryUpper: Int? = null
            var salaryLower: Int? = null

            if (binding.editSalaryStart.text.toString() != "") {
                salaryUpper = binding.editSalaryStart.text.toString().toInt()
            }

            if (binding.editSalaryEnd.text.toString() != "") {
                salaryLower = binding.editSalaryEnd.text.toString().toInt()
            }

            viewModel.createJobPost(
                JobPostItem(
                    title = binding.editJobTitle.text.toString(),
                    salary_lower = salaryUpper,
                    salary_upper = salaryLower,
                    type = binding.editJobType.text.toString(),
                    shift_start = binding.editShiftFrom.text.toString(),
                    shift_end = binding.editShiftTo.text.toString(),
                    description = binding.editJobDescription.text.toString(),
                )
            )
        }


        viewModel.validationResponse.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                Toast.makeText(
                    requireContext(),
                    "New Job Posted Successfully!",
                    Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.popBackStack()
            } else {
                Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
            }
        })
    }

}