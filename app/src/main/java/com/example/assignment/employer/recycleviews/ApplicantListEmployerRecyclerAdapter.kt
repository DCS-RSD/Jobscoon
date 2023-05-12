package com.example.assignment.employee.recycleviews

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
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
import com.example.assignment.databinding.ItemApplicantListBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.databinding.NavigationEmployeeBinding
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.User
import com.example.assignment.employee.EmployeeNavHost
import com.example.assignment.employee.FindJobsEmployeeViewModel
import com.example.assignment.employee.JobDetailsEmployeeFragment
import com.example.assignment.employer.JobPostedEmployerViewModel

class ApplicantListEmployerRecyclerAdapter(private val viewModel: JobPostedEmployerViewModel, private val dataList: List<User>) : RecyclerView.Adapter<ApplicantListEmployerRecyclerAdapter.ViewHolder>() {

    lateinit var binding : ItemApplicantListBinding

    inner class ViewHolder(val binding: ItemApplicantListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.user = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemApplicantListBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
