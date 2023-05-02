package com.example.assignment.employee

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding

class JobDetailsEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailsEmployeeBinding


    companion object {
        fun newInstance() = JobDetailsEmployeeFragment()
    }

    private lateinit var viewModel: JobDetailsEmployeeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentJobDetailsEmployeeBinding>(inflater,
            R.layout.fragment_job_details_employee,container,false)

        binding.imageView.setOnClickListener {
                view : View -> view.findNavController().popBackStack()
        }

        binding.applyButton.setOnClickListener{
            showAlert()
        }

        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JobDetailsEmployeeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Apply Job")
        builder.setMessage("Are you sure to apply this job ?")
        builder.setPositiveButton("Yes") { dialog, which ->
            Toast.makeText(requireContext(), "You have applied the job !", Toast.LENGTH_LONG).show()
            //TODO: backend, disableApplyButtonOnCreate, changeToDisableColor
            binding.applyButton.isEnabled = false
            binding.applyButton.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.disabled_button_color)

        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

}