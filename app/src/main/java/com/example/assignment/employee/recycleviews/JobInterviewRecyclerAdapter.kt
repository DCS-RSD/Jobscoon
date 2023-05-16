package com.example.assignment.employee.recycleviews

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.databinding.ItemInterviewEmployeeBinding
import com.example.assignment.dataclass.JobInterviewItem
import com.example.assignment.employee.InterviewEmployeeViewModel

class JobInterviewRecyclerAdapter(
    private val viewModel: InterviewEmployeeViewModel,
    private val context: Context,
) : RecyclerView.Adapter<JobInterviewRecyclerAdapter.ViewHolder>() {

    lateinit var binding: ItemInterviewEmployeeBinding
    private var dataList = listOf<JobInterviewItem>()
    fun setItem(JobInterviewItem: List<JobInterviewItem>) {
        this.dataList = JobInterviewItem
    }

    inner class ViewHolder(val binding: ItemInterviewEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobInterviewItem) {
            binding.interviewItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemInterviewEmployeeBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        if (item.type == "virtual") {
            holder.binding.iconLocation.setImageResource(R.drawable.icon_link)
            holder.binding.locationOrLink.text = item.link
        } else if (item.type == "physical") {
            holder.binding.iconLocation.setImageResource(R.drawable.icon_location)
            holder.binding.locationOrLink.text = item.location
        }

        if (item.status == "accept") {
            holder.binding.acceptButton.visibility = View.GONE
            holder.binding.declinedButton.visibility = View.GONE
            holder.binding.accepted.visibility = View.VISIBLE
            holder.binding.accepted.text = "ACCEPTED"
            holder.binding.accepted.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.accepted_text_color)
        } else if (item.status == "declined") {
            holder.binding.acceptButton.visibility = View.GONE
            holder.binding.declinedButton.visibility = View.GONE
            holder.binding.accepted.visibility = View.VISIBLE
            holder.binding.accepted.text = "DECLINED"
            holder.binding.accepted.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.rejected_text_color)
        }else{
            holder.binding.acceptButton.visibility = View.VISIBLE
            holder.binding.declinedButton.visibility = View.VISIBLE
            holder.binding.accepted.visibility = View.GONE
        }

        holder.apply {
            binding.acceptButton.setOnClickListener{
                val dialog = CustomDialog.customDialog(
                    context,
                    "Accept Interview",
                    "Are You Sure To Accept Interview?"
                )
                dialog.show()
                dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                    viewModel.id=item.id!!
                    viewModel.postAcceptData()
                    Toast.makeText(context, "You have accepted the interview !", Toast.LENGTH_LONG).show()
                    holder.binding.acceptButton.visibility = View.GONE
                    holder.binding.declinedButton.visibility = View.GONE
                    holder.binding.accepted.visibility = View.VISIBLE
                    dialog.dismiss()
                }

                dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                    dialog.dismiss()
                }
            }
            binding.declinedButton.setOnClickListener{
                val dialog = CustomDialog.customDialog(
                    context,
                    "Decline Interview",
                "Are You Sure To Decline Interview?"
                )
                dialog.show()
                dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                    viewModel.id=item.id!!
                    viewModel.postDeclineData()
                    Toast.makeText(context, "You have declined the interview !", Toast.LENGTH_LONG).show()
                    holder.binding.acceptButton.visibility = View.GONE
                    holder.binding.declinedButton.visibility = View.GONE
                    holder.binding.accepted.visibility = View.VISIBLE
                    holder.binding.accepted.text = "DECLINED"
                    holder.binding.accepted.backgroundTintList = ContextCompat.getColorStateList(context, R.color.rejected_text_color)
                    dialog.dismiss()
                }

                dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                    dialog.dismiss()
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
