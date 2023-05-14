package com.example.assignment.employer.recycleviews


import android.content.res.ColorStateList
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R

import com.example.assignment.databinding.ItemCareerDevelopmentEmployeeBinding

import com.example.assignment.dataclass.CareerDevelopmentItem

import com.example.assignment.employer.CareerDevelopmentEmployerViewModel


class CareerDevelopmentEmployerRecyclerAdapter(private val viewModel: CareerDevelopmentEmployerViewModel, private val dataList: List<CareerDevelopmentItem>) : RecyclerView.Adapter<CareerDevelopmentEmployerRecyclerAdapter.ViewHolder>() {

    lateinit var binding : ItemCareerDevelopmentEmployeeBinding

    inner class ViewHolder(val binding: ItemCareerDevelopmentEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CareerDevelopmentItem) {
            binding.careerDevelopmentItem = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCareerDevelopmentEmployeeBinding.inflate(inflater, parent, false)



        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        //holder.binding.jobId.text = item.id.toString()
        holder.binding.joinBtn.setOnClickListener {
            viewModel.careerDevId.value = item.id
            it.findNavController().navigate(R.id.action_careerDevelopmentEmployerFragment_to_careerDevelopmentDetailsEmployerFragment)
        }

        if (item.capacity == 0) {
            holder.binding.status.apply {
                setTextColor(
                    ContextCompat.getColor(
                        holder.binding.status.context,
                        R.color.rejected_text_color
                    )
                )
                backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        holder.binding.status.context,
                        R.color.rejected_layout_color
                    )
                )
                text = "full"
            }

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
