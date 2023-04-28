package com.example.assignment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentSignUpCompanyBinding
import com.example.assignment.dataclass.Company

class SignUpCompanyFragment : Fragment() {

    lateinit var binding: FragmentSignUpCompanyBinding
     val sharedViewModel : SignUpEmployerViewModel by activityViewModels()

    companion object {
        fun newInstance() = SignUpCompanyFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_company, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.submitSignUp.setOnClickListener {
            sharedViewModel.submitCompany(
                Company(
                    binding.editCompanyName.text.toString(),
                    binding.editRegNo.text.toString(),
                    binding.editPhone.text.toString(),
                    binding.editLocation.text.toString(),
                    binding.editAbout.text.toString(),
                )
            )

            sharedViewModel.signUpResponse.observe(viewLifecycleOwner, Observer {
                if (it.success) {
                    findNavController().navigate(R.id.action_signUpCompanyFragment_to_signUpEmployerFragment2)
                } else {
                    Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()

                }
            })
        }



    }

}