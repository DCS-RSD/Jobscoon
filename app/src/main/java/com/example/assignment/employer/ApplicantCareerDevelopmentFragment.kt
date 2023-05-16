package com.example.assignment.employer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.*
import com.example.assignment.employer.recycleviews.ApplicantCareerDevelopmentRecyclerAdapter

class ApplicantCareerDevelopmentFragment : Fragment() {

    companion object {
        fun newInstance() = ApplicantCareerDevelopmentFragment()
    }

    private lateinit var binding: FragmentApplicantCareerDevelopmentBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private val sharedViewModel: CareerDevelopmentEmployerViewModel by activityViewModels()
    private lateinit var viewModel: ApplicantCareerDevelopmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_applicant_career_development,
            container,
            false
        )
        manager = LinearLayoutManager(requireContext())

        binding.imageView.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ApplicantCareerDevelopmentViewModel::class.java)
        val id = sharedViewModel.careerDevId.value!!

        viewModel.getApplicantData(id)

        viewModel.applicantList.observe(viewLifecycleOwner, Observer {
            binding.applicantListRecycleView.visibility = View.VISIBLE
            binding.loadingIcon.visibility = View.GONE
            if (it.isEmpty()) {
                binding.textNoRecord.visibility = View.VISIBLE
            } else {
                binding.applicantListRecycleView.apply {
                    adapter = ApplicantCareerDevelopmentRecyclerAdapter(viewModel, requireContext(), it)
                    layoutManager = manager
                }
            }
        })

        binding.applicantListRefresh.setOnRefreshListener {
            viewModel.getApplicantData(id)
            binding.applicantListRefresh.isRefreshing = false
        }

    }


}