package com.example.assignment.employee

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.auth.AuthActivity
import com.example.assignment.databinding.FragmentProfileEmployeeBinding

class ProfileEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileEmployeeFragment()
    }

    lateinit var viewModel: AuthViewModel
    lateinit var binding: FragmentProfileEmployeeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_employee, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        viewModel.getProfile()
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            binding.profileScroll.visibility = View.VISIBLE

            if (it.description == null) {
                it.description =
                "Describe yourself can let other know more about you!"
                binding.textAbout.apply {
                    setTypeface(null, Typeface.ITALIC)
                }
            }
            if (it.address == null) {
                it.address = "No Location"
                binding.addressS.apply {
                    setTypeface(null, Typeface.ITALIC)
                }
            }

            binding.user = it

        })

        binding.refreshProfile.setOnRefreshListener {
            viewModel.getProfile()
            binding.refreshProfile.isRefreshing = false
        }


        binding.changePwdButton.setOnClickListener { view ->
            binding.loadingIcon.visibility = View.VISIBLE
            binding.profileScroll.visibility = View.INVISIBLE
            view.findNavController()
                .navigate(R.id.action_profileEmployeeFragment_to_changePasswordFragment)
        }

        binding.editS.setOnClickListener { view ->
            binding.loadingIcon.visibility = View.VISIBLE
            binding.profileScroll.visibility = View.INVISIBLE
            view.findNavController()
                .navigate(R.id.action_profileEmployeeFragment_to_editProfileEmployeeFragment)
        }

        binding.changePwdButton.setOnClickListener{view ->
            binding.loadingIcon.visibility = View.VISIBLE
            binding.profileScroll.visibility = View.INVISIBLE
            view.findNavController().navigate(R.id.action_profileEmployeeFragment_to_changePasswordFragment)
        }

        binding.logout.setOnClickListener {
            val dialog = CustomDialog.customDialog(requireContext(),"Logout","Are You Sure To Logout?")
            dialog.show()
            dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                viewModel.logout(
                    requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
                        .getString("Token", "")!!
                )
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                dialog.dismiss()
            }


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
    }

}