package com.example.assignment.employee.recycleviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.databinding.ItemApplicantListBinding
import com.example.assignment.databinding.ItemJobPostBinding
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.User
import com.example.assignment.employer.ApplicantListEmployerViewModel

class ApplicantListEmployerRecyclerAdapter(
    private val viewModel: ApplicantListEmployerViewModel,
    private val context: Context,

    ) : RecyclerView.Adapter<ApplicantListEmployerRecyclerAdapter.ViewHolder>() {

    lateinit var binding: ItemApplicantListBinding
    private var dataList = listOf<User>()
    fun setItem(user: List<User>) {
        this.dataList = user
    }

    inner class ViewHolder(val binding: ItemApplicantListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.user = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemApplicantListBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    private fun setAccept(holder: ViewHolder) {
        holder.apply {
            binding.acceptButton.visibility = View.GONE
            binding.rejectButton.visibility = View.GONE
            binding.actionBtn.apply {
                visibility = View.VISIBLE
                isEnabled = true
            }
        }
    }

    private fun setDecline(holder: ViewHolder) {
        holder.apply {
            binding.acceptButton.visibility = View.GONE
            binding.rejectButton.visibility = View.GONE
            binding.actionBtn.apply {
                backgroundTintList = ContextCompat
                    .getColorStateList(context, R.color.rejected_text_color)
                text = "DECLINED"
                visibility = View.VISIBLE
                isEnabled = false
            }
        }
    }

    private fun setPending(holder: ViewHolder) {
        holder.apply {
            binding.acceptButton.visibility = View.VISIBLE
            binding.rejectButton.visibility = View.VISIBLE
            binding.actionBtn.visibility = View.GONE
        }
    }

    fun checkStatus(status: String, holder: ViewHolder) {
        when (status) {
            in "accept" -> setAccept(holder)
            in "declined" -> setDecline(holder)
            else -> setPending(holder)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        val status = item.pivot?.status.toString()
        val applicationId = item.pivot?.id!!
        println(item)
        holder.apply {
            bind(item)
            if (item.description == null) {
                binding.textDescription.visibility = View.GONE
                binding.line.visibility = View.GONE
            }

            checkStatus(status, holder)

            //accept button
            binding.acceptButton.setOnClickListener {
                val dialog = CustomDialog.customDialog(
                    context,
                    "Accept Applicant",
                    "Are You Sure Accept " + item.name + " ?"
                )
                dialog.show()

                dialog.findViewById<Button>(R.id.btn_done).setOnClickListener { view ->
                    viewModel.accept(applicationId)
                    view.isClickable = false
                    viewModel.acceptResponse.observe(context as LifecycleOwner, Observer {
                        if (it.success) {
                            setAccept(holder)
                            Toast.makeText(
                                context,
                                "Applicant Accepted Successfully!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(context, it.errorMsg, Toast.LENGTH_LONG).show()
                        }
                        dialog.dismiss()
                    })
                }

                dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                    dialog.dismiss()
                }
            }

            //reject
            binding.rejectButton.setOnClickListener {
                val dialog = CustomDialog.customDialog(
                    context,
                    "Reject Applicant",
                    "Are You Sure Reject " + item.name + " ?"
                )
                dialog.show()

                dialog.findViewById<Button>(R.id.btn_done).setOnClickListener { view ->
                    viewModel.decline(applicationId)
                    view.isClickable = false
                    dialog.dismiss()
                    viewModel.rejectResponse.observe(context as LifecycleOwner, Observer {
                        if (it.success) {
                            setDecline(holder)
                            Toast.makeText(
                                context,
                                "Applicant Rejected Successfully!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(context, it.errorMsg, Toast.LENGTH_LONG).show()
                        }
                    })
                }

                dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                    dialog.dismiss()
                }
            }

            binding.actionBtn.setOnClickListener {
                viewModel.id = item.id!!
                it.findNavController()
                    .navigate(R.id.action_applicantListEmployerFragment_to_scheduleInterviewEmployerFragment)
            }
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
