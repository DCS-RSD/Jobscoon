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

        binding.editShiftFrom.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, days))
        binding.editShiftTo.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, days))
        binding.editTextLocationS.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, jobTypes))

        binding.iconArrowback.setOnClickListener {view ->
            view.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(PostJobEmployerViewModel::class.java)
        binding.confirmJobBtn.setOnClickListener{

            if (binding.editDate.text.toString().isEmpty() || binding.editTextLocationS.text.toString().isEmpty() || binding.editShiftFrom.text.toString().isEmpty() ||
                binding.editShiftTo.text.toString().isEmpty() || binding.editSalaryStart.text.toString().isEmpty() || binding.editSalaryEnd.text.toString().isEmpty() || binding.editJobDescription.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all the fields.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            else if (binding.editSalaryStart.text.toString().toInt() > binding.editSalaryEnd.text.toString().toInt()) {
                Toast.makeText(
                    requireContext(),
                    "Salary from cannot greater than salary to.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            else {
                val comShift = binding.editShiftFrom.text.toString() + " to " + binding.editShiftTo.text.toString()

                val jobPost = JobPostItem(
                    title = binding.editDate.text.toString(),
                    type = binding.editTextLocationS.text.toString(),
                    shift = comShift,
                    salary_lower = binding.editSalaryStart.text.toString().toInt(),
                    salary_upper = binding.editSalaryEnd.text.toString().toInt(),
                    description = binding.editJobDescription.text.toString()
                )

                viewModel.add(jobPost)

                viewModel.validationResponse.observe(viewLifecycleOwner, Observer {
                    try {
                        if (it.success) {
                            viewModel.validationResponse.value = null //reset
                            Toast.makeText(
                                requireContext(),
                                "Job Posted Successfully!",
                                Toast.LENGTH_LONG
                            ).show()
                            view?.findNavController()?.popBackStack()
                        } else {
                            Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
                        }

                    }catch (e:Exception){}

                })
            }


        }
    }

}