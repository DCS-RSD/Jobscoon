package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.EditJobViewModel
import com.example.assignment.R

class EditJobFragment : Fragment() {

    companion object {
        fun newInstance() = EditJobFragment()
    }

    private lateinit var viewModel: EditJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_job, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditJobViewModel::class.java)
        // TODO: Use the ViewModel
    }

}