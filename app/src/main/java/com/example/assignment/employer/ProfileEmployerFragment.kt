package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.assignment.R
import com.example.assignment.databinding.FragmentProfileEmployerBinding
import com.example.assignment.employee.AuthViewModel

class ProfileEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileEmployerFragment()
    }

    lateinit var viewModel: AuthViewModel
    lateinit var binding: FragmentProfileEmployerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_employer, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        viewModel.getProfile()
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            try {
                binding.user = it
            } catch (e: Exception) {
            }
        })
/*
        binding.changePwdButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(R.id.action_profileEmployeeFragment_to_changePasswordFragment)
        }

        binding.editS.setOnClickListener { view ->
            view.findNavController()
                .navigate(R.id.action_profileEmployeeFragment_to_editProfileEmployeeFragment)
        }

        binding.changePwdButton.setOnClickListener{view ->
            view.findNavController().navigate(R.id.action_profileEmployeeFragment_to_changePasswordFragment)
        }

        binding.editS.setOnClickListener{view ->
            view.findNavController().navigate(R.id.action_profileEmployeeFragment_to_editProfileEmployeeFragment)
        }

        binding.logout.setOnClickListener {


            viewModel.logout(
                requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
                    .getString("Token", "")!!
            )


            viewModel.responseUI.observe(viewLifecycleOwner, Observer {
                if (it.success) {
                    //clear token
                    requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE).edit()
                        .clear().apply()
                    val intent = Intent(requireActivity(), AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
                }
            })
        }

 */
    }

}