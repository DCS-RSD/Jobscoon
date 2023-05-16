package com.example.assignment.employee

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.auth.AuthActivity
import com.example.assignment.auth.SignUpEmployerViewModel
import com.example.assignment.databinding.FragmentFindJobsEmployeeBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.employee.recycleviews.JobPostRecyclerAdapter
import com.example.assignment.employer.recycleviews.JobPostEmployerRecyclerAdapter

class FindJobsEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = FindJobsEmployeeFragment()
    }


    private lateinit var binding: FragmentFindJobsEmployeeBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: JobPostRecyclerAdapter
    val sharedViewModel: FindJobsEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_find_jobs_employee,
            container,
            false
        )
        manager = LinearLayoutManager(requireContext())
        recycleViewAdapter = JobPostRecyclerAdapter(sharedViewModel)
        binding.jobPostRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        sharedViewModel.getData()

        sharedViewModel.jobPostList.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            if(it.isEmpty()){
                binding.textNoRecord.visibility = View.VISIBLE
                binding.jobPostRecycleView.visibility = View.INVISIBLE
            }else{
                binding.textNoRecord.visibility = View.GONE
                binding.jobPostRecycleView.visibility = View.VISIBLE
                recycleViewAdapter.setItem(it)
                binding.jobPostRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }

        })

        binding.jobPostRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.jobPostRefresh.isRefreshing = false
        }

        sharedViewModel.getAllResponse.observe(viewLifecycleOwner, Observer { response ->
            if (!response.success) {
                sharedViewModel.isExpired.observe(viewLifecycleOwner, Observer {
                    if (it) {
                        val dialog = CustomDialog.customDialog(
                            requireContext(),
                            "Session Expired",
                            "Please Login Again"
                        )
                        dialog.findViewById<Button>(R.id.btn_cancel).visibility = View.GONE
                        dialog.show()
                        dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                            val intent = Intent(requireActivity(), AuthActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(requireContext(), response.errorMsg, Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        })


    }

}