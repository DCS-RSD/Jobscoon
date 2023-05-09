package com.example.assignment.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentSignUpCompanyBinding
import com.example.assignment.databinding.FragmentSignUpEmployerBinding
import com.example.assignment.dataclass.Company
import com.example.assignment.dataclass.ResponseForUI

class SignUpEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpEmployerFragment()
    }

    private lateinit var binding: FragmentSignUpEmployerBinding
    val sharedViewModel: SignUpEmployerViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up_employer, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


//        println(sharedViewModel.selectedCompany)
        //UI add company name
        binding.submitSignUp.setOnClickListener {

            val input = SignUpItem(
                binding.editLocation.text.toString(),
                binding.editAbout.text.toString(),
                binding.editEmail.text.toString(),
                binding.editUsername.text.toString(),
                binding.editPassword.text.toString(),
                binding.editPhone.text.toString(),
            )


            sharedViewModel.signUpEmployer(
                input,
                binding.editPasswordConfirmation.text.toString(),
            )


            sharedViewModel.signUpEmployerResponse.observe(viewLifecycleOwner, Observer {
                try {
                    if (it.success) {
                        sharedViewModel.signUpEmployerResponse.value = null
                        view?.findNavController()
                            ?.navigate(R.id.action_signUpEmployerFragment2_to_loginFragment)
                        Toast.makeText(
                            requireContext(),
                            "Employer Registered Successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
//                    Log.i("exception", "onActivityCreated: " + e)
                }
            })

        }
    }

}