package com.example.assignment.employee.recycleviews

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.ItemJobAppliedBinding
import com.example.assignment.dataclass.JobApplicationItem
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.employee.FindJobsEmployeeViewModel

class JobAppliedRecyclerAdapter(private val sharedViewModel: FindJobsEmployeeViewModel) : RecyclerView.Adapter<JobAppliedRecyclerAdapter.ViewHolder>() {

    lateinit var binding : ItemJobAppliedBinding
    private var dataList = listOf<JobApplicationItem>()
    private var dataList2 = listOf<JobInterviewItem>()


    fun setItem(jobApplicationList: List<JobApplicationItem>, jobInterviewList: List<JobInterviewItem>){
        this.dataList = jobApplicationList
        this.dataList2 = jobInterviewList

    }



    inner class ViewHolder(val binding: ItemJobAppliedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobApplicationItem) {
            binding.jobApplicationItem = item
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemJobAppliedBinding.inflate(inflater, parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)

        for (interview in dataList2) {
            if(holder.binding.jobApplicationItem?.job_post_id == interview.job_post_id) {
                    if(interview.status != "declined")
                        holder.binding.interviewIcon.visibility = View.VISIBLE
            }

        }


        var jobId = item.job_post_id.toString().toInt()

        holder.binding.jobs1.setOnClickListener {
            sharedViewModel.jobPostId.value = jobId
            it.findNavController().navigate(R.id.action_jobsAppliedEmployeeFragment_to_jobDetailsEmployeeFragment)

        }


        val textColorId = when(item.status) {
            "accept" -> R.color.accepted_text_color
            "pending" -> R.color.pending_text_color
            "declined" -> R.color.rejected_text_color
            else -> R.color.pending_text_color
        }
        val textBackgroundTintId = when(item.status) {
            "accept" -> R.color.accepted_layout_color
            "pending" -> R.color.pending_layout_color
            "declined" -> R.color.rejected_layout_color
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