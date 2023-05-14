package com.example.assignment.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentLoginBinding
import com.example.assignment.databinding.FragmentSignUpBinding
import com.example.assignment.dataclass.ResponseForUI

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding.submitSignUp.setOnClickListener {
            viewModel.submitSignUp(
                SignUpItem(
                    binding.editLocation.text.toString(),
                    binding.editAbout.text.toString(),
                    binding.editEmail.text.toString(),
                    binding.editUsername.text.toString(),
                    binding.editPassword.text.toString(),
                    binding.editPhone.text.toString(),
                ),
                binding.editPasswordConfirmation.text.toString(),
            )

        }

        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Employee Registered Successfully", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()

            }
        })
    }


}