package com.example.assignment.employer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.auth.AuthActivity
import com.example.assignment.databinding.FragmentJobPostedEmployerBinding
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.employer.recycleviews.JobPostEmployerRecyclerAdapter
import java.util.*

class JobPostedEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = JobPostedEmployerFragment()
    }

    private lateinit var binding: FragmentJobPostedEmployerBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: JobPostEmployerRecyclerAdapter
    private val sharedViewModel: JobPostedEmployerViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_job_posted_employer,
            container,
            false
        )

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        manager = LinearLayoutManager(requireContext())
        recycleViewAdapter = JobPostEmployerRecyclerAdapter(sharedViewModel)
        binding.jobPostRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(JobPostedEmployerViewModel::class.java)

        sharedViewModel.getData()


        sharedViewModel.jobPostList.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            if (it.isEmpty()) {
                binding.textNoRecord.visibility = View.VISIBLE
                binding.jobPostRecycleView.visibility = View.GONE
                binding.searchView2.visibility = View.INVISIBLE
            } else {
                binding.searchView2.visibility = View.VISIBLE
                binding.jobPostRecycleView.visibility = View.VISIBLE
                binding.textNoRecord.visibility = View.GONE
                recycleViewAdapter.setItem(it)
                binding.jobPostRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        })

        //navigate to form
        binding.floatingActionButton.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_jobPostedEmployerFragment_to_postJobEmployerFragment)
        }

        binding.jobPostRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.jobPostRefresh.isRefreshing = false

            try {
                binding.searchView2.setQuery("", false)
                val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
            }catch (e:Exception){}

        }

        //check api response
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

        binding.searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

    }

    private fun filterList(query: String?) {
        if (query != null) {
            try{
                val filteredList = ArrayList<JobPostItem>()
                for (i in sharedViewModel.jobPostList.value!!) {
                    if (i.title?.lowercase(Locale.ROOT)!!.contains(query)) {
                        filteredList.add(i)
                    }
                }
                if (filteredList == null) {
                    Toast.makeText(requireContext(), "No", Toast.LENGTH_LONG).show()
                } else {
                    recycleViewAdapter.setItem(filteredList)
                    binding.jobPostRecycleView.apply {
                        adapter?.notifyDataSetChanged()
                    }
                }
            }catch (e:Exception){}


        }
    }

}