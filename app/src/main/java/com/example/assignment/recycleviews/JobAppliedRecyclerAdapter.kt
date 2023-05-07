package com.example.assignment.recycleviews

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
import com.example.assignment.databinding.ItemJobAppliedBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.dataclass.JobPostItem
import java.text.SimpleDateFormat
import java.util.*

class JobAppliedRecyclerAdapter(private val dataList: List<JobApplicationItem>, private val dataList2: List<JobInterviewItem>) : RecyclerView.Adapter<JobAppliedRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemJobAppliedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobApplicationItem) {
            binding.jobApplicationItem = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJobAppliedBinding.inflate(inflater, parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)

        for (item in dataList2) {
            if (holder.binding.jobApplicationItem?.job_post_id == item.job_post_id) {
                holder.binding.interviewIcon.visibility = View.VISIBLE
            }
        }


        var jobId = item.job_post_id.toString()
        holder.binding.jobs1.setOnClickListener{ view ->
            val bundle = Bundle()
            bundle.putString("position", jobId)
            view.findNavController().navigate(R.id.action_jobsAppliedEmployeeFragment_to_jobDetailsEmployeeFragment, bundle)
        }

        val textColorId = when(item.status) {
            "accept" -> R.color.accepted_text_color
            "pending" -> R.color.pending_text_color
            "decline" -> R.color.rejected_text_color
            else -> R.color.pending_text_color
        }
        val textBackgroundTintId = when(item.status) {
            "accept" -> R.color.accepted_layout_color
            "pending" -> R.color.pending_layout_color
            "decline" -> R.color.rejected_layout_color
            else -> R.color.pending_layout_color
        }
        val textColor = ContextCompat.getColor(holder.binding.status1.context, textColorId)
        val backgroundTintColor = ContextCompat.getColor(holder.binding.status1.context, textBackgroundTintId)

        holder.binding.status1.apply {
            setTextColor(textColor)
            backgroundTintList = ColorStateList.valueOf(backgroundTintColor)
        }


    }



    override fun getItemCount(): Int {
        return dataList.size
    }

}