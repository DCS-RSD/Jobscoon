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
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.databinding.FragmentCareerDevelopmentDetailsBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding

class CareerDevelopmentDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCareerDevelopmentDetailsBinding
    private lateinit var viewModel: CareerDevelopmentDetailsViewModel

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




        binding.iconArrowback.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CareerDevelopmentDetailsViewModel::class.java)
        val id = sharedViewModel.careerDevId.value!!
        viewModel.showCareerDev(id)
        viewModel.careerDevDetail.observe(viewLifecycleOwner, Observer {

            binding.loadingIcon.visibility = View.GONE
            binding.scroll.visibility = View.VISIBLE
            binding.careerDevelopmentItem = it
            if(it.type == "physical") {
                binding.textView14.text = it.location
            }else if (it.type == "virtual") {
                binding.imageView15.setImageResource(R.drawable.icon_link)
                binding.textView14.text = it.link
            }

            var check = it.is_applied!!
            var capCheck = it.capacity

            if (check) {
                binding.joinBtn.apply {
                    backgroundTintList = ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.rejected_text_color
                    )
                    text = "UNJOIN"
                }
            }

            if (capCheck == 0) {
                binding.joinBtn.isEnabled = false
                binding.joinBtn.backgroundTintList =
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.disabled_button_color
                    )
            }



        })

        binding.joinBtn.setOnClickListener{
            if (binding.joinBtn.text == "JOIN NOW") {
                val dialog = CustomDialog.customDialog(
                    requireContext(),
                    "JOIN PROGRAMME",
                    "Are You Sure To Join This Programme?"
                )
                dialog.show()
                dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                    viewModel.postJoinData(id)
                    Toast.makeText(requireContext(), "You have join the programme !", Toast.LENGTH_LONG).show()
                    var showCap = binding.textView22.text.toString().toInt()
                    showCap -= 1
                    binding.textView22.text = showCap.toString()
                    binding.joinBtn.text = "UNJOIN"
                    binding.joinBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.rejected_text_color)
                    dialog.dismiss()
                }

                dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                    dialog.dismiss()
                }
            }
            else if (binding.joinBtn.text == "UNJOIN") {
                val dialog = CustomDialog.customDialog(
                    requireContext(),
                    "UNJOIN PROGRAMME",
                    "Are You Sure To Unjoin This Programme?"
                )
                dialog.show()
                dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                    viewModel.postUnjoinData(id)
                    Toast.makeText(requireContext(), "You have unjoin the programme !", Toast.LENGTH_LONG).show()
                    var showCap = binding.textView22.text.toString().toInt()
                    showCap += 1
                    binding.textView22.text = showCap.toString()
                    binding.joinBtn.text = "JOIN NOW"
                    binding.joinBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.salary_text_color)
                    dialog.dismiss()
                }

                dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        binding.careerDevDetailsRefresh.setOnRefreshListener {
            viewModel.showCareerDev(id)
            binding.careerDevDetailsRefresh.isRefreshing = false
        }
    }

}