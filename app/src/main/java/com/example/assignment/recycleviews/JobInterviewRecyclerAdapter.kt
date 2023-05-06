package com.example.assignment.recycleviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.ItemInterviewEmployeeBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.dataclass.JobPostItem

class JobInterviewRecyclerAdapter(private val dataList: List<JobInterviewItem>) : RecyclerView.Adapter<JobInterviewRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemInterviewEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobInterviewItem) {
            binding.interviewItem = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInterviewEmployeeBinding.inflate(inflater, parent, false)
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

        if (item.type == "virtual") {
            holder.binding.iconLocation.setImageResource(R.drawable.icon_link)
            holder.binding.locationOrLink.text = item.link
        }else if (item.type == "physical") {
            holder.binding.iconLocation.setImageResource(R.drawable.icon_location)
            holder.binding.locationOrLink.text = item.location
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
