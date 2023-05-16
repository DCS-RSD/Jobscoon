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
import com.example.assignment.databinding.FragmentJobDetailsEmployeeBinding
import com.example.assignment.databinding.FragmentJobDetailsEmployerBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter

class JobDetailsEmployerFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailsEmployerBinding
    private lateinit var viewModel: JobDetailsEmployerViewModel

    companion object {
        fun newInstance() = JobDetailsEmployerFragment()
    }

    val sharedViewModel: JobPostedEmployerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate<FragmentJobDetailsEmployerBinding>(
            inflater,
            R.layout.fragment_job_details_employer, container, false
        )


        binding.applyButton.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_jobDetailsEmployerFragment_to_applicantListEmployerFragment)
        }


        binding.imageView.setOnClickListener { view ->
            view.findNavController().popBackStack()
        }



        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JobDetailsEmployerViewModel::class.java)
        val id = sharedViewModel.jobPostId.value!!
        //iconMEnu
        binding.iconMore.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit -> {
                        findNavController().navigate(R.id.action_jobDetailsEmployerFragment_to_editJobFragment)
                        true
                    }
                    R.id.delete -> {
                        val dialog = CustomDialog.customDialog(
                            requireContext(),
                            "Delete Job",
                            "Are You Sure To Delete This Job?"
                        )
                        dialog.show()
                        dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                            viewModel.deleteJobPost(id)
                            viewModel.deleteResponse.observe(viewLifecycleOwner, Observer {
                                if (it.success) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Job Deleted Successfully!",
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

        viewModel.showJobPost(id)
        viewModel.jobPostDetail.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            binding.scroll.visibility = View.VISIBLE
            binding.jobPostItem = it
        })


        binding.jobDetailsRefresh.setOnRefreshListener {
            viewModel.showJobPost(id)
            binding.jobDetailsRefresh.isRefreshing = false
        }
        //error handling
    }


}
