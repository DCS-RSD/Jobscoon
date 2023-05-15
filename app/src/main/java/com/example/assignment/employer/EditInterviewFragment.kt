package com.example.assignment.employer

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentEditInterviewBinding
import com.example.assignment.dataclass.JobInterviewItem
import java.util.*

class EditInterviewFragment : Fragment() {

    companion object {
        fun newInstance() = EditInterviewFragment()
    }

    private lateinit var viewModel: EditInterviewViewModel
    private val sharedViewModel: InterviewEmployerViewModel by activityViewModels()
    private lateinit var binding: FragmentEditInterviewBinding
    val types = arrayOf("physical", "virtual")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_interview, container, false)


        binding.iconArrowback.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.editType.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                types
            )
        )

        if (binding.editType.text.toString() == "physical") {
            binding.textLocationS.visibility = View.VISIBLE
            binding.textLinkS.visibility = View.GONE
        } else if (binding.editType.text.toString() == "virtual") {
            binding.textLocationS.visibility = View.GONE
            binding.textLinkS.visibility = View.VISIBLE
        }

        binding.editType.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Do something after the text is changed
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do something before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.editType.text.toString() == "physical") {
                    binding.textLocationS.visibility = View.VISIBLE
                    binding.textLinkS.visibility = View.GONE
                } else if (binding.editType.text.toString() == "virtual") {
                    binding.textLocationS.visibility = View.GONE
                    binding.textLinkS.visibility = View.VISIBLE
                }
            }
        })

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        binding.editStartTime.keyListener = null
        binding.editEndTime.keyListener = null

        var clickedTime: Int = 0

        binding.editStartTime.setOnClickListener {
            val timePicker = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                if (selectedHour in 0..9 && selectedMinute in 0..9) {
                    binding.editStartTime.setText("0$selectedHour:0$selectedMinute")
                } else if (selectedHour in 0..9) {
                    binding.editStartTime.setText("0$selectedHour:$selectedMinute")
                } else if (selectedMinute in 0..9) {
                    binding.editStartTime.setText("$selectedHour:0$selectedMinute")
                } else {
                    binding.editStartTime.setText("$selectedHour:$selectedMinute")
                }
                clickedTime = 1

                binding.editEndTime.setOnClickListener {
                    val endTimePicker =
                        TimePickerDialog(requireContext(), { _, endHour, endMinute ->

                            if (endHour > selectedHour || ((endHour == selectedHour) && (endMinute > selectedMinute))) {
                                if (endHour in 0..9 && endMinute in 0..9) {
                                    binding.editEndTime.setText("0$endHour:0$endMinute")
                                } else if (endHour in 0..9) {
                                    binding.editEndTime.setText("0$endHour:$endMinute")
                                } else if (endMinute in 0..9) {
                                    binding.editEndTime.setText("$endHour:0$endMinute")
                                } else {
                                    binding.editEndTime.setText("$endHour:$endMinute")
                                }
                            } else
                                Toast.makeText(
                                    requireContext(),
                                    "End Time must greater than Start Time",
                                    Toast.LENGTH_LONG
                                ).show()

                        }, hour, minute, false)

                    endTimePicker.show()
                }

            }, hour, minute, false)


            timePicker.show()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditInterviewViewModel::class.java)
        viewModel.id = sharedViewModel.id //get id

        viewModel.show() //get the chosen interview data
        viewModel.jobInterviewData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.jobInterviewItem = it
            binding.editType.setText(it.type, false)
        })

        binding.confirmInterviewBtn.setOnClickListener {
            viewModel.editJobInterview(JobInterviewItem(
                    date = binding.editDate.text.toString(),
                    start_time = binding.editStartTime.text.toString(),
                    end_time = binding.editEndTime.text.toString(),
                    type = binding.editType.text.toString(),
                    location = binding.editLocation.text.toString(),
                    link = binding.editLink.text.toString(),
                    description = binding.editDescription.text.toString()
                )
            )
        }

        viewModel.validationResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.success) {
                Toast.makeText(
                    requireContext(),
                    "Interview Scheduled Successfully",
                    Toast.LENGTH_LONG
                ).show()
                view?.findNavController()?.popBackStack()
            } else {
                Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_LONG).show()
            }
        })

    }

}