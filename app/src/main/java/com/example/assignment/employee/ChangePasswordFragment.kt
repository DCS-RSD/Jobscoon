package com.example.assignment.employee

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
import com.example.assignment.ChangePasswordViewModel
import com.example.assignment.EditProfileEmployeeViewModel
import com.example.assignment.R
import com.example.assignment.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {

    companion object {
        fun newInstance() = ChangePasswordFragment()
    }

    private lateinit var viewModel: ChangePasswordViewModel
    private val sharedViewModel: EditProfileEmployeeViewModel by activityViewModels()

    private lateinit var binding: FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate<FragmentChangePasswordBinding>(
            inflater,
            R.layout.fragment_change_password, container, false
        )

        binding.imageView.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
        binding.submitChangePasswordBtn.setOnClickListener {
            sharedViewModel.resetPassword(
                binding.editCurrentPassword.text.toString(),
                binding.editNewPassword.text.toString(),
                binding.editNewPasswordConfirmation.text.toString()
            )

            sharedViewModel.resetResponse.observe(viewLifecycleOwner, Observer {
                try {
                    if (it.success) {
                        sharedViewModel.resetResponse.value = null //reset
                        Toast.makeText(
                            requireContext(),
                            "Password Reset Successfully!",
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