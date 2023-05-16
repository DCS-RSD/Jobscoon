package com.example.assignment.employee.recycleviews

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.databinding.FragmentFindJobsEmployeeBinding
import com.example.assignment.databinding.ItemInterviewEmployerBinding
import com.example.assignment.databinding.ItemInterviewHistoryBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.databinding.NavigationEmployeeBinding
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.employee.EmployeeNavHost
import com.example.assignment.employee.FindJobsEmployeeViewModel
import com.example.assignment.employee.InterviewEmployeeViewModel
import com.example.assignment.employee.JobDetailsEmployeeFragment
import com.example.assignment.employer.InterviewEmployerViewModel
import com.example.assignment.employer.JobPostedEmployerViewModel

class InterviewEmployeeHistoryRecyclerAdapter(
    private val viewModel: InterviewEmployeeViewModel,
    private val context: Context
) :
    RecyclerView.Adapter<InterviewEmployeeHistoryRecyclerAdapter.ViewHolder>() {

    lateinit var binding: ItemInterviewHistoryBinding
    private var dataList = listOf<JobInterviewItem>()
    fun setItem(JobInterviewItem: List<JobInterviewItem>) {
        this.dataList = JobInterviewItem
    }

    inner class ViewHolder(val binding: ItemInterviewHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobInterviewItem) {
            binding.interviewItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemInterviewHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        if (item.type == "virtual") {
            holder.binding.iconLocation.setImageResource(R.drawable.icon_link)
            holder.binding.locationOrLink.text = item.link
        } else if (item.type == "physical") {
            holder.binding.iconLocation.setImageResource(R.drawable.icon_location)
            holder.binding.locationOrLink.text = item.location
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
