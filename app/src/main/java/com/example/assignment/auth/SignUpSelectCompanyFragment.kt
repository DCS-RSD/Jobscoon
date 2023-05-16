package com.example.assignment.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentSignUpSelectCompanyBinding

class SignUpSelectCompanyFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpSelectCompanyFragment()
    }

    val sharedViewModel: SignUpEmployerViewModel by activityViewModels()
    private lateinit var binding: FragmentSignUpSelectCompanyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up_select_company,
            container,
            false
        )

        binding.registerCompanyBtn.setOnClickListener {

            it.findNavController()
                .navigate(R.id.action_signUpSelectCompanyFragment_to_signUpCompanyFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel.getCompanyNameList()
        binding.refresh.setOnRefreshListener {
            try {
                binding.searchCompanyView.setQuery("",false)
                val inputMethodManager: InputMethodManager = requireContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
            }catch (e:Exception){}
            sharedViewModel.getCompanyNameList()
            binding.refresh.isRefreshing = false
        }

        sharedViewModel.companyNameList.observe(viewLifecycleOwner, Observer {

            val companyArr = sharedViewModel.getCompanyArr()

//            println(companyArr)
            val companyAdapter: ArrayAdapter<String> = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                companyArr
            )

            binding.companyListView.adapter = companyAdapter

            binding.searchCompanyView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchCompanyView.clearFocus()
                    if (companyArr.contains(query)) {
                        companyAdapter.filter.filter(query)
                    }
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    companyAdapter.filter.filter(query)
                    return false
                }


            })
            binding.companyListView.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = companyAdapter.getItem(position)
                // handle click on the selected item
                sharedViewModel.selectCompany(selectedItem!!)
                sharedViewModel.isNew = false
                view.findNavController()
                    .navigate(R.id.action_signUpSelectCompanyFragment_to_signUpEmployerFragment2)
            }

        })
    }


}