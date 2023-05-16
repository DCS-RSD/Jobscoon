package com.example.assignment.employee.tabs

import android.app.DatePickerDialog
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
import androidx.viewpager2.widget.ViewPager2
import com.example.assignment.AddCareerDevelopmentEmployerViewModel
import com.example.assignment.PostJobEmployerViewModel
import com.example.assignment.R
import com.example.assignment.databinding.FragmentAddCareerDevelopmentEmployerBinding
import com.example.assignment.databinding.FragmentEditInterviewBinding
import com.example.assignment.databinding.FragmentInterviewMainBinding
import com.example.assignment.databinding.FragmentInterviewMainEmployeeBinding
import com.example.assignment.dataclass.CareerDevelopmentItem
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.employee.InterviewEmployeeViewModel
import com.example.assignment.employer.InterviewEmployerViewModel
import com.example.assignment.employer.tabs.InterviewMainFragment
import com.google.android.material.tabs.TabLayout
import java.util.*

class InterviewMainEmployeeFragment : Fragment() {

    companion object {
        fun newInstance() = InterviewMainFragment()
    }

    private lateinit var binding: FragmentInterviewMainEmployeeBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private val sharedViewModel: InterviewEmployeeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_interview_main_employee, container, false
        )

        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager

        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"))
        tabLayout.addTab(tabLayout.newTab().setText("History"))

        viewPager2.adapter = TabAdapter(requireActivity().supportFragmentManager, lifecycle)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!=null){
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
        sharedViewModel.getData()
        sharedViewModel.getHistoryData()

        sharedViewModel.navigating.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it){
                binding.tabLayout.visibility = View.INVISIBLE
            }else   {
                binding.tabLayout.visibility = View.VISIBLE
            }
        })

        return binding.root
    }

}