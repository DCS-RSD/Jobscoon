package com.example.assignment.employee

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.FragmentCareerDevelopmentEmployeeBinding
import com.example.assignment.dataclass.CareerDevelopmentItem
import com.example.assignment.employee.recycleviews.CareerDevelopmentEmployeeRecyclerAdapter
import java.util.*

class CareerDevelopmentEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = CareerDevelopmentEmployeeFragment()
    }

    private lateinit var binding: FragmentCareerDevelopmentEmployeeBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: CareerDevelopmentEmployeeRecyclerAdapter
    val sharedViewModel: CareerDevelopmentEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_career_development_employee,
            container,
            false
        )

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        manager = LinearLayoutManager(requireContext())

        recycleViewAdapter = CareerDevelopmentEmployeeRecyclerAdapter(sharedViewModel)
        binding.careerDevelopmentEmployeeRecycleView.apply {
            adapter = recycleViewAdapter
            layoutManager = manager
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedViewModel.getData()

        sharedViewModel.careerDevelopmentList.observe(viewLifecycleOwner, Observer {
            binding.loadingIcon.visibility = View.GONE
            if (it.isEmpty()) {
                binding.careerDevelopmentEmployeeRecycleView.visibility = View.INVISIBLE
                binding.textNoRecord.visibility = View.VISIBLE
                binding.searchView3.visibility = View.INVISIBLE
            } else {
                binding.searchView3.visibility = View.VISIBLE
                binding.careerDevelopmentEmployeeRecycleView.visibility = View.VISIBLE
                binding.textNoRecord.visibility = View.GONE

                recycleViewAdapter.setItem(it)
                binding.careerDevelopmentEmployeeRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        })

        binding.careerDevelopmentEmployeeRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.careerDevelopmentEmployeeRefresh.isRefreshing = false

            try {
                binding.searchView3.setQuery("", false)
                val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
            }catch (e:Exception){}

        }

        sharedViewModel.navigating.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.searchView3.visibility = View.INVISIBLE
                binding.careerDevelopmentEmployeeRecycleView.visibility = View.INVISIBLE
            }
        })


        binding.searchView3.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            try {
                val filteredList = ArrayList<CareerDevelopmentItem>()
                for (i in sharedViewModel.careerDevelopmentList.value!!) {
                    if (i.title?.lowercase(Locale.ROOT)!!.contains(query)) {
                        filteredList.add(i)
                    }
                }
                if (filteredList == null) {
                    Toast.makeText(requireContext(), "No", Toast.LENGTH_LONG).show()
                } else {
                    recycleViewAdapter.setItem(filteredList)
                    binding.careerDevelopmentEmployeeRecycleView.apply {
                        adapter?.notifyDataSetChanged()
                    }
                }
            }catch (e:Exception){}

        }
    }

}