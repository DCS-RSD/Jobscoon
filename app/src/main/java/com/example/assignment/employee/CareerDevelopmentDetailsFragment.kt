package com.example.assignment.employee

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.assignment.CareerDevelopmentDetailsViewModel
import com.example.assignment.R
import com.example.assignment.databinding.FragmentCareerDevelopmentDetailsBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding

class CareerDevelopmentDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCareerDevelopmentDetailsBinding

    companion object {
        fun newInstance() = CareerDevelopmentDetailsFragment()
    }

    val sharedViewModel: CareerDevelopmentEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate<FragmentCareerDevelopmentDetailsBinding>(
            inflater,
            R.layout.fragment_career_development_details, container, false)



        val dialog = Dialog(requireContext())
        binding.joinBtn.setOnClickListener {
            dialog.show()
        }

        dialog.setContentView(R.layout.join_career_dialog)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialog_background
            )
        )
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.window?.attributes?.windowAnimations = R.style.animation

        dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
            Toast.makeText(requireContext(), "You have join the career development !", Toast.LENGTH_LONG).show()

            sharedViewModel.postJoinData()
            var showCap = binding.textView22.text.toString().toInt()
            showCap -= 1
            binding.textView22.text = showCap.toString()
            binding.joinBtn.visibility = View.GONE
            binding.unjoinBtn.visibility = View.VISIBLE
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }

        val dialog2 = Dialog(requireContext())
        binding.unjoinBtn.setOnClickListener {
            dialog2.show()
        }

        dialog2.setContentView(R.layout.unjoin_career_dialog)
        dialog2.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialog_background
            )
        )
        dialog2.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog2.setCancelable(false)
        dialog2.window?.attributes?.windowAnimations = R.style.animation

        dialog2.findViewById<Button>(R.id.btn_done).setOnClickListener {
            Toast.makeText(requireContext(), "You have unjoin the career development !", Toast.LENGTH_LONG).show()

            sharedViewModel.postUnjoinData()

            var showCap2 = binding.textView22.text.toString().toInt()
            showCap2 += 1
            binding.textView22.text = showCap2.toString()
            binding.unjoinBtn.visibility = View.GONE
            binding.joinBtn.visibility = View.VISIBLE

            dialog2.dismiss()
        }

        dialog2.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog2.dismiss()
        }

        binding.iconArrowback.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel.showCareerDev()
        sharedViewModel.careerDevDetail.observe(viewLifecycleOwner, Observer {

            try {
                binding.careerDevelopmentDetailsFragment.apply {
                    binding.careerDevelopmentItem = it
                    binding.textView22.text = it.capacity.toString()
                    if(it.type == "physical") {
                        binding.textView14.text = it.location
                    }else if (it.type == "virtual") {
                        binding.imageView15.setImageResource(R.drawable.icon_link)
                        binding.textView14.text = it.link
                    }

                }


                var check = it.is_applied!!
                var capCheck = it.capacity

                if (check) {
                    binding.joinBtn.visibility = View.GONE
                    binding.unjoinBtn.visibility = View.VISIBLE
                }

                if (capCheck == 0) {
                    binding.joinBtn.backgroundTintList =
                        ContextCompat.getColorStateList(
                            requireContext(),
                            R.color.disabled_button_color
                        )
                }
            } catch (e: Exception) {
            }


        })

        binding.careerDevDetailsRefresh.setOnRefreshListener {
            sharedViewModel.showCareerDev()
            binding.careerDevDetailsRefresh.isRefreshing = false
        }
    }

}