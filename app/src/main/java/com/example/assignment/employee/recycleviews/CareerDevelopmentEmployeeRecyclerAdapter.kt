package com.example.assignment.employee.recycleviews

import android.content.Context
import android.content.res.ColorStateList
import android.provider.ContactsContract.RawContacts.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.FragmentFindJobsEmployeeBinding
import com.example.assignment.databinding.ItemCareerDevelopmentEmployeeBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.databinding.NavigationEmployeeBinding
import com.example.assignment.dataclass.*
import com.example.assignment.employee.CareerDevelopmentEmployeeViewModel
import com.example.assignment.employee.EmployeeNavHost

class CareerDevelopmentEmployeeRecyclerAdapter(private val sharedViewModel : CareerDevelopmentEmployeeViewModel) : RecyclerView.Adapter<CareerDevelopmentEmployeeRecyclerAdapter.ViewHolder>() {

    lateinit var binding: ItemCareerDevelopmentEmployeeBinding
    private var dataList = listOf<CareerDevelopmentItem>()


    fun setItem(careerDevList: List<CareerDevelopmentItem>){
        this.dataList = careerDevList

    }
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
        holder.binding.joinBtn.setOnClickListener{
            sharedViewModel.careerDevId.value = item.id
            it.findNavController().navigate(R.id.action_careerDevelopmentEmployeeFragment_to_careerDevelopmentDetailsFragment)
        }
        if (item.is_applied == true) {
            holder.binding.status.apply {
                setTextColor(ContextCompat.getColor(holder.binding.status.context, R.color.accepted_text_color))
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.binding.status.context, R.color.accepted_layout_color))
                text = "applied"
            }
        }
        else if (item.capacity == 0) {
            holder.binding.status.apply {
                setTextColor(ContextCompat.getColor(holder.binding.status.context, R.color.rejected_text_color))
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.binding.status.context, R.color.rejected_layout_color))
                text = "full"
            }
        }else {
            holder.binding.status.apply {
                setTextColor(ContextCompat.getColor(holder.binding.status.context, R.color.available_text_color))
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(holder.binding.status.context, R.color.available_layout_color))
                text = "available"
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
