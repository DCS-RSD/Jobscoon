package com.example.assignment.recycleviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.api.JobPostItem

class JobPostRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var jobPostList: List<JobPostItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JobPostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_employer_home, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is JobPostViewHolder -> {
                holder.bind(jobPostList[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return jobPostList.size
    }

    fun submitList(apiJobPostList: List<JobPostItem>){
        jobPostList = apiJobPostList
    }


    class JobPostViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val jobTitle = itemView.findViewById<TextView>(R.id.job_name)
        val jobSalary = itemView.findViewById<TextView>(R.id.salary)
        val jobType = itemView.findViewById<TextView>(R.id.type)
        val jobShift = itemView.findViewById<TextView>(R.id.shift)
//        val jobPostedTime = itemView.findViewById<TextView>(R.id.posted_time)

        fun bind(jobPost: JobPostItem) {
            jobTitle.text = jobPost.title
            jobSalary.text = jobPost.salary_lower.toString() + "-" + jobPost.salary_upper.toString()
            jobType.text = jobPost.type
            jobShift.text = jobPost.shift

        }
    }
}