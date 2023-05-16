package com.example.assignment.employee

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
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.databinding.CustomDialogBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter
import com.example.assignment.employer.JobDetailsEmployerViewModel

class JobDetailsEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailsEmployeeBinding
    private lateinit var viewModel: JobDetailsEmployeeViewModel

    companion object {
        fun newInstance() = JobDetailsEmployeeFragment()
    }

    val sharedViewModel: FindJobsEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate<FragmentJobDetailsEmployeeBinding>(
            inflater,
            R.layout.fragment_job_details_employee, container, false
        )



        binding.imageView.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }



        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JobDetailsEmployeeViewModel::class.java)
        val id = sharedViewModel.jobPostId.value!!
        viewModel.showJobPost(id)
        viewModel.jobPostDetail.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            binding.scroll.visibility = View.VISIBLE
            binding.jobPostItem = it
            var check = it.is_applied!!

            if (check) {
                binding.applyButton.isEnabled = false
                binding.applyButton.backgroundTintList =
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.disabled_button_color
                    )
            }
        })

        binding.applyButton.setOnClickListener {
            val dialog = CustomDialog.customDialog(
                requireContext(),
                "APPLY NOW",
                "Are You Sure To Apply This Job ?"
            )
            dialog.show()

            dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                viewModel.postData(id)
                Toast.makeText(requireContext(), "You have applied the job !", Toast.LENGTH_LONG).show()
                binding.applyButton.isEnabled = false
                binding.applyButton.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.disabled_button_color)
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                dialog.dismiss()
            }

        }


        binding.jobDetailsRefresh.setOnRefreshListener {
            viewModel.showJobPost(id)
            binding.jobDetailsRefresh.isRefreshing = false
        }
    }


}
