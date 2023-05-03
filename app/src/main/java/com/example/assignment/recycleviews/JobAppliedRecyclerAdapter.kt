package com.example.assignment.recycleviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.ItemJobAppliedBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.dataclass.JobPostItem

class JobAppliedRecyclerAdapter(private val dataList: List<JobApplicationItem>) : RecyclerView.Adapter<JobAppliedRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemJobAppliedBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: JobApplicationItem) {
            binding.jobApplicationItem = item
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJobAppliedBinding.inflate(inflater, parent, false)
        /*
        binding.jobCard.setOnClickListener{
                view : View -> view.findNavController().navigate(R.id.action_findJobsEmployeeFragment_to_jobDetailsEmployeeFragment)

        }
        */

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}