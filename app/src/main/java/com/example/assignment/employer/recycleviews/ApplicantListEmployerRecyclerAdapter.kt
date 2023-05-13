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
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.CustomDialog
import com.example.assignment.R
import com.example.assignment.databinding.ItemApplicantListBinding
import com.example.assignment.dataclass.User
import com.example.assignment.employer.ApplicantListEmployerViewModel

class ApplicantListEmployerRecyclerAdapter(
    private val viewModel: ApplicantListEmployerViewModel,
    private val context: Context,
    private val dataList: List<User>
) : RecyclerView.Adapter<ApplicantListEmployerRecyclerAdapter.ViewHolder>() {

    lateinit var binding: ItemApplicantListBinding

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

    private fun setAccept() {
        binding.acceptButton.visibility = View.GONE
        binding.rejectButton.visibility = View.GONE
        binding.actionBtn.apply {
            visibility = View.VISIBLE
        }
    }

    private fun setDecline() {
        binding.acceptButton.visibility = View.GONE
        binding.rejectButton.visibility = View.GONE
        binding.actionBtn.apply {
            backgroundTintList = ContextCompat
                .getColorStateList(context, R.color.rejected_text_color)
            text = "DECLINED"
            visibility = View.VISIBLE
            isClickable = false
        }
    }

    fun checkStatus(status: String) {
        when (status) {
            in "accept" -> setAccept()
            in "declined" -> setDecline()
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

            checkStatus(status)

            //accept button
            binding.acceptButton.setOnClickListener {
                val dialog = CustomDialog.customDialog(
                    context,
                    "Accept Applicant",
                    "Are You Sure Accept " + item.name + " ?"
                )
                dialog.show()

                dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                    viewModel.accept(applicationId)
                    viewModel.acceptResponse.observe(context as LifecycleOwner, Observer {
                        if (it.success) {
                            setAccept()
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

                dialog.findViewById<Button>(R.id.btn_done).setOnClickListener {
                    viewModel.decline(applicationId)
                    viewModel.rejectResponse.observe(context as LifecycleOwner, Observer {
                        if (it.success) {
                            setDecline()
                            Toast.makeText(
                                context,
                                "Applicant Rejected Successfully!",
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
        }


    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
