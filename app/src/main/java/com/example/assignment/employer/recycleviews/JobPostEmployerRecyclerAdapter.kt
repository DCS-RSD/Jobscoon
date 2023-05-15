package com.example.assignment.employer.recycleviews

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.FragmentFindJobsEmployeeBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.databinding.NavigationEmployeeBinding
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.employee.EmployeeNavHost
import com.example.assignment.employee.FindJobsEmployeeViewModel
import com.example.assignment.employee.JobDetailsEmployeeFragment
import com.example.assignment.employer.JobPostedEmployerViewModel

class JobPostEmployerRecyclerAdapter(private val viewModel: JobPostedEmployerViewModel) :
    RecyclerView.Adapter<JobPostEmployerRecyclerAdapter.ViewHolder>() {

    lateinit var binding: ItemJobPostBinding
    private var dataList = listOf<JobPostItem>()
    fun setItem(jobPostList: List<JobPostItem>) {
        this.dataList = jobPostList
    }

    inner class ViewHolder(val binding: ItemJobPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobPostItem) {
            binding.jobPostItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemJobPostBinding.inflate(inflater, parent, false)



        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        holder.binding.jobId.text = item.id.toString()
        holder.binding.jobCard.setOnClickListener {
            viewModel.jobPostId.value = item.id
            it.findNavController()
                .navigate(R.id.action_jobPostedEmployerFragment_to_jobDetailsEmployerFragment)
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
