package com.example.assignment.employer.recycleviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.databinding.ItemApplicantCareerDevelopmentBinding
import com.example.assignment.databinding.ItemApplicantListBinding
import com.example.assignment.dataclass.User
import com.example.assignment.employer.ApplicantCareerDevelopmentViewModel
import com.example.assignment.employer.ApplicantListEmployerViewModel

class ApplicantCareerDevelopmentRecyclerAdapter(
    private val viewModel: ApplicantCareerDevelopmentViewModel,
    private val context: Context,
    private val dataList: List<User>
) : RecyclerView.Adapter<ApplicantCareerDevelopmentRecyclerAdapter.ViewHolder>() {

    lateinit var binding: ItemApplicantCareerDevelopmentBinding

    inner class ViewHolder(val binding: ItemApplicantCareerDevelopmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.user = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemApplicantCareerDevelopmentBinding.inflate(inflater, parent, false)

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
