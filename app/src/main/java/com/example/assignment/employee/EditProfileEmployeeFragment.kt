package com.example.assignment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.assignment.databinding.FragmentChangePasswordBinding
import com.example.assignment.databinding.FragmentEditProfileEmployeeBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding
import com.example.assignment.dataclass.User
import com.example.assignment.employee.FindJobsEmployeeViewModel

class EditProfileEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = EditProfileEmployeeFragment()
    }

    private lateinit var viewModel: EditProfileEmployeeViewModel
    private val sharedViewModel: FindJobsEmployeeViewModel by activityViewModels()
    private lateinit var binding: FragmentEditProfileEmployeeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentEditProfileEmployeeBinding>(
            inflater,
            R.layout.fragment_edit_profile_employee, container, false
        )

        binding.imageView.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditProfileEmployeeViewModel::class.java)
        binding.userData = sharedViewModel.currentUser
        binding.submitUpdateProfileBtn.setOnClickListener {
            viewModel.update(
                User(
                    binding.editLocation.text.toString(),
                    binding.editDescription.text.toString(),
                    binding.editEmail.text.toString(),
                    binding.editPhone.text.toString(),
                    binding.editName.text.toString()
                )
            )

            viewModel.validationResponse.observe(viewLifecycleOwner, Observer {
                if (it.success) {
                    Toast.makeText(
                        requireContext(),
                        "Profile Updated Successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
                }
            })
        }

    }

}