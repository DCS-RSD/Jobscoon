package com.example.assignment.employer

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.get
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.databinding.CustomDialogBinding
import com.example.assignment.databinding.FragmentCareerDevelopmentDetailsEmployerBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployerBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter

class CareerDevelopmentDetailsEmployerFragment : Fragment() {

    private lateinit var binding: FragmentCareerDevelopmentDetailsEmployerBinding
    private lateinit var viewModel: CareerDevelopmentDetailsEmployerViewModel

    companion object {
        fun newInstance() = CareerDevelopmentDetailsEmployerFragment()
    }

    val sharedViewModel: CareerDevelopmentEmployerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentCareerDevelopmentDetailsEmployerBinding>(
            inflater,
            R.layout.fragment_career_development_details_employer, container, false
        )


        binding.joinBtn.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_careerDevelopmentDetailsEmployerFragment_to_applicantCareerDevelopmentFragment)
        }




        binding.iconArrowback.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }


        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CareerDevelopmentDetailsEmployerViewModel::class.java)
        val id = sharedViewModel.careerDevId.value!!

        //iconMEnu
        binding.iconMore.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit -> {
                        findNavController().navigate(R.id.action_careerDevelopmentDetailsEmployerFragment_to_editCareerDevelopmentEmployerFragment)
                        true
                    }
                    R.id.delete -> {
                        val dialog = CustomDialog.customDialog(
                            requireContext(),
                            "Delete Programme",
                            "Are You Sure To Delete This Programme?"
                        )
                        dialog.show()
                        dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                            viewModel.deleteCareerDev(id)
                            viewModel.deleteResponse.observe(viewLifecycleOwner, Observer {
                                if (it.success){
                                    Toast.makeText(
                                        requireContext(),
                                        "Programme Deleted Successfully!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    view?.findNavController()?.popBackStack()
                                }else{
                                    Toast.makeText(
                                        requireContext(),
                                        it.errorMsg,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                            dialog.dismiss()
                        }
                        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                            dialog.dismiss()
                        }

                        true
                    }
                    else ->
                        false
                }

            }
            popupMenu.inflate(R.menu.menu_job_details_employer)

            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e("Acticity", "Error showing menu icons", e)
            } finally {
                popupMenu.show()
            }
        }

        viewModel.showCareerDev(id)
        viewModel.careerDevDetail.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            binding.scroll.visibility = View.VISIBLE
            binding.careerDevelopmentItem = it
            if(it.type == "physical") {
                binding.imageView15.visibility = View.VISIBLE
                binding.iconLink.visibility = View.INVISIBLE
                binding.textView14.text = it.location
            }else if (it.type == "virtual") {
                binding.imageView15.visibility = View.INVISIBLE
                binding.iconLink.visibility = View.VISIBLE
                binding.textView14.text = it.link
            }
        })


        binding.careerDevDetailsRefresh.setOnRefreshListener {
            viewModel.showCareerDev(id)
            binding.careerDevDetailsRefresh.isRefreshing = false
        }
        //error handling
    }


}
