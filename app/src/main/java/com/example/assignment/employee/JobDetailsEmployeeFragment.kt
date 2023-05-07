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
import com.example.assignment.R
import com.example.assignment.databinding.CustomDialogBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.recycleviews.JobPostRecyclerAdapter

class JobDetailsEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailsEmployeeBinding

    companion object {
        fun newInstance() = JobDetailsEmployeeFragment()
    }

    val sharedViewModel: JobDetailsEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentJobDetailsEmployeeBinding>(inflater,
            R.layout.fragment_job_details_employee,container,false)

        val args = arguments
        val id = args?.getString("position")
        binding.jobId.text = id.toString()

        var jobId = binding.jobId.text.toString().toInt()

        sharedViewModel.autoLogin()
        sharedViewModel.getJobAppliedData()

        sharedViewModel.jobApplicationList.observe(viewLifecycleOwner, Observer {
            for (item in it) {
                if (jobId == item.job_post_id) {
                    binding.applyButton.isEnabled = false
                    binding.applyButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.disabled_button_color)
                    return@Observer
                }
            }
        })

        val dialog = Dialog(requireContext())
        binding.applyButton.setOnClickListener{
            dialog.show()
        }

        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dialog_background))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        dialog.window?.attributes?.windowAnimations = R.style.animation

        dialog.findViewById<Button>(R.id.btn_done).setOnClickListener{
            Toast.makeText(requireContext(), "You have applied the job !", Toast.LENGTH_LONG).show()
            //TODO: backend, postJob

            binding.applyButton.isEnabled = false
            binding.applyButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.disabled_button_color)
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener{
            dialog.dismiss()
        }

        binding.imageView.setOnClickListener {
                view : View -> view.findNavController().popBackStack()
        }



        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel.autoLogin() //check token

        var jobId = binding.jobId.text.toString().toInt()

        sharedViewModel.getData(jobId)

        sharedViewModel.jobPostList.observe(viewLifecycleOwner, Observer {
            binding.jobDetailsEmployeeFragment.apply {
                binding.textView4.text = it.title
                binding.textView5.text = it.company?.name
                binding.textView100.text = it.company?.contact_number
                binding.textView101.text = it.company?.email
                binding.textView9.text = it.salary
                binding.textView13.text = it.type
                binding.textView15.text = it.shift
                binding.jobShift2.text = it.description
            }

            Log.d("acticity", "onActivityCreated: "+it)
        })





        binding.jobDetailsRefresh.setOnRefreshListener {
            sharedViewModel.getData(binding.jobId.text.toString().toInt())
            binding.jobDetailsRefresh.isRefreshing = false
        }
    }



}