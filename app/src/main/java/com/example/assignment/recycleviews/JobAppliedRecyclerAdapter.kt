package com.example.assignment.recycleviews

import android.content.res.ColorStateList
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
import com.example.assignment.dataclass.JobPostItem
import java.text.SimpleDateFormat
import java.util.*

class JobAppliedRecyclerAdapter(private val dataList: List<JobApplicationItem>) : RecyclerView.Adapter<JobAppliedRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemJobAppliedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobApplicationItem) {
            binding.jobApplicationItem = item
        }
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)

        val timestampString = dataList[position].created_at // Get the timestamp string from your data object

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.US) // Specify the input format of the timestamp string
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val date = inputFormat.parse(timestampString) // Parse the timestamp string into a Date object
        val dateString = outputFormat.format(date)
        holder.binding.applyDateItself.text = dateString

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