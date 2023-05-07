package com.example.assignment.recycleviews

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.ItemInterviewEmployeeBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.employee.InterviewEmployeeViewModel

class JobInterviewRecyclerAdapter(private val context: Context, private val dataList: List<JobInterviewItem>) : RecyclerView.Adapter<JobInterviewRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemInterviewEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobInterviewItem) {
            binding.interviewItem = item
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInterviewEmployeeBinding.inflate(inflater, parent, false)

        //sharedViewModel.autoLogin()
        //sharedViewModel.getData()
/*
        sharedViewModel.jobInterviewList.observe(lifecycleOwner, Observer {
            //TODO:wait ziyan status

            for (item in it) {
                if ()
            }


        })

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

        val dialog = Dialog(context)

        holder.binding.acceptButton.setOnClickListener{
            dialog.show()
        }

        dialog.setContentView(R.layout.interview_accept_dialog)
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_background))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)
        dialog.window?.attributes?.windowAnimations = R.style.animation

        dialog.findViewById<Button>(R.id.btn_done).setOnClickListener{
            Toast.makeText(context, "You have accept the interview !", Toast.LENGTH_LONG).show()
            //sharedViewModel.postAcceptData(item.id)
            holder.binding.acceptButton.visibility = View.GONE
            holder.binding.declinedButton.visibility = View.GONE
            holder.binding.accepted.visibility = View.VISIBLE
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener{
            dialog.dismiss()
        }

        val dialog2 = Dialog(context)

        holder.binding.declinedButton.setOnClickListener{
            dialog2.show()
        }

        dialog2.setContentView(R.layout.interview_decline_dialog)
        dialog2.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_background))
        dialog2.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog2.setCancelable(false)
        dialog2.window?.attributes?.windowAnimations = R.style.animation

        dialog2.findViewById<Button>(R.id.btn_done).setOnClickListener{
            Toast.makeText(context, "You have declined the interview !", Toast.LENGTH_LONG).show()
            //sharedViewModel.postDeclineData(item.id)
            holder.binding.acceptButton.visibility = View.GONE
            holder.binding.declinedButton.visibility = View.GONE
            holder.binding.accepted.visibility = View.VISIBLE
            holder.binding.accepted.text = "DECLINED"
            holder.binding.accepted.backgroundTintList = ContextCompat.getColorStateList(context, R.color.rejected_text_color)
            dialog2.dismiss()
        }

        dialog2.findViewById<Button>(R.id.btn_cancel).setOnClickListener{
            dialog2.dismiss()
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
