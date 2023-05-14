package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.R

class InterviewEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = InterviewEmployerFragment()
    }

    private lateinit var viewModel: InterviewEmployerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_interview_employer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InterviewEmployerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}