package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentEditProfileEmployerBinding
import com.example.assignment.dataclass.Company

class EditProfileCompanyFragment : Fragment() {

    companion object {
        fun newInstance() = EditProfileCompanyFragment()
    }

    private lateinit var viewModel: EditProfileCompanyViewModel
    private lateinit var binding: FragmentEditProfileEmployerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_profile_employer,
            container,
            false
        )

        binding.iconArrowback.setOnClickListener {
            it.findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditProfileCompanyViewModel::class.java)

        viewModel.getMyCompanyProfile()
        viewModel.companyDetails.observe(viewLifecycleOwner, Observer {
            binding.company = it
        })

        //response of get company
        viewModel.getResponse.observe(viewLifecycleOwner, Observer {
            if (!it.success) {
                Toast.makeText(
                    requireContext(),
                    it.errorMsg,
                    Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.popBackStack()
            }
        })

        binding.submitUpdateProfileBtn.setOnClickListener {
            viewModel.updateMyCompanyProfile(
                Company(
                    binding.editTextComNameS.text.toString(),
                    binding.editTextSSM.text.toString(),
                    binding.editCompanyPhone.text.toString(),
                    binding.editTextLocationS.text.toString(),
                    binding.editTextIntroS.text.toString(),
                    binding.editTextEmailS.text.toString(),
                )
            )
        }

        viewModel.postResponse.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                Toast.makeText(
                    requireContext(),
                    "Company Profile Updated Successfully!",
                    Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    it.errorMsg,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}