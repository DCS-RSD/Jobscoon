package com.example.assignment.employer

import android.content.Context
import android.content.Intent
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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.auth.AuthActivity
import com.example.assignment.databinding.FragmentCareerDevelopmentEmployerBinding
import com.example.assignment.dataclass.CareerDevelopmentItem
import com.example.assignment.employer.recycleviews.CareerDevelopmentEmployerRecyclerAdapter
import java.util.*

class CareerDevelopmentEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = CareerDevelopmentEmployerFragment()
    }

    private lateinit var binding: FragmentCareerDevelopmentEmployerBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var recycleViewAdapter: CareerDevelopmentEmployerRecyclerAdapter
    val sharedViewModel: CareerDevelopmentEmployerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_career_development_employer,
            container,
            false
        )

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        manager = LinearLayoutManager(requireContext())
        recycleViewAdapter = CareerDevelopmentEmployerRecyclerAdapter(sharedViewModel)
        binding.careerDevelopmentEmployerRecycleView.apply {
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
            binding.floatingActionButton.visibility = View.VISIBLE
            binding.searchView4.visibility = View.INVISIBLE
            if (it.isEmpty()) {
                binding.textNoRecord.visibility = View.VISIBLE
                binding.careerDevelopmentEmployerRecycleView.visibility = View.INVISIBLE
            } else {
                binding.searchView4.visibility = View.VISIBLE
                binding.textNoRecord.visibility = View.GONE
                binding.careerDevelopmentEmployerRecycleView.visibility = View.VISIBLE

                recycleViewAdapter.setItem(it)
                binding.careerDevelopmentEmployerRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        })

        //navigate to form
        binding.floatingActionButton.setOnClickListener {
            binding.textNoRecord.visibility = View.GONE
            binding.floatingActionButton.visibility = View.GONE
            binding.careerDevelopmentEmployerRecycleView.visibility = View.INVISIBLE
            binding.loadingIcon.visibility = View.VISIBLE
            binding.searchView4.visibility = View.INVISIBLE
            it.findNavController()
                .navigate(R.id.action_careerDevelopmentEmployerFragment_to_addCareerDevelopmentEmployerFragment)
        }

        binding.careerDevelopmentEmployerRefresh.setOnRefreshListener {
            sharedViewModel.getData()
            binding.careerDevelopmentEmployerRefresh.isRefreshing = false

            try{
                binding.searchView4.setQuery("", false)
                val inputMethodManager: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
            }catch (e:Exception){}

        }

        sharedViewModel.getAllResponse.observe(viewLifecycleOwner, Observer { response ->
            if (!response.success) {
                sharedViewModel.isExpired.observe(viewLifecycleOwner, Observer {
                    if (it) {
                        //dialog
                        val intent = Intent(requireActivity(), AuthActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), response.errorMsg, Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        })

        sharedViewModel.navigating.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.floatingActionButton.visibility = View.GONE
                binding.careerDevelopmentEmployerRecycleView.visibility = View.INVISIBLE
                binding.searchView4.visibility = View.INVISIBLE
            }
        })

        binding.searchView4.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
                binding.careerDevelopmentEmployerRecycleView.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }


}