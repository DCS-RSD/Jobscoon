package com.example.assignment.employer.recycleviews

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils.substring
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.ItemApplicantListBinding
import com.example.assignment.databinding.ItemJobAppliedBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.User
import com.example.assignment.employee.FindJobsEmployeeViewModel
import com.example.assignment.employee.JobsAppliedEmployeeViewModel
import com.example.assignment.employer.ApplicantListEmployerFragment
import com.example.assignment.employer.ApplicantListEmployerViewModel
import java.text.SimpleDateFormat
import java.util.*

class ApplicantListEmployerRecyclerAdapter(private val viewModel:ApplicantListEmployerViewModel, private val dataList: List<User>) : RecyclerView.Adapter<ApplicantListEmployerRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemApplicantListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.user = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemApplicantListBinding.inflate(inflater, parent, false)


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