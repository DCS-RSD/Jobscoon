package com.example.assignment.recycleviews

import android.content.Context
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.dataclass.JobPostItem

class JobPostRecyclerAdapter(private val dataList: List<JobPostItem>) : RecyclerView.Adapter<JobPostRecyclerAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemJobPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: JobPostItem) {
            binding.jobPostItem = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJobPostBinding.inflate(inflater, parent, false)
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
