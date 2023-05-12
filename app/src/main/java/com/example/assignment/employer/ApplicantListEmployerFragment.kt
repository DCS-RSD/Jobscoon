package com.example.assignment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ApplicantListEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = ApplicantListEmployerFragment()
    }

    private lateinit var viewModel: ApplicantListEmployerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
        val acceptBtn = findViewById<Button>(R.id.accept_button)
        acceptBtn.setOnClickListener {
            startActivity(Intent(this,InterviewFormActivity::class.java))
        }
        */
        return inflater.inflate(R.layout.fragment_applicant_list_employer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApplicantListEmployerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}