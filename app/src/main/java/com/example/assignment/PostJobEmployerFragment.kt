package com.example.assignment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment.api.Route
import com.example.assignment.dataclass.JobPostItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostJobEmployerFragment : Fragment() {

    companion object {
        fun newInstance() = PostJobEmployerFragment()
    }

    private lateinit var viewModel: PostJobEmployerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val build = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getString(R.string.api_link))
            .build()
            .create(Route::class.java)

        val testData = JobPostItem(
            1,
            "",
            "Hello",
            null,
            2000,
            3000,
            "Monday",
            "Test Job",
            "Part-Time",
            "",
            "",
            "",
        )

        build.createJobPost(testData).enqueue(object : Callback<JobPostItem?> {
            override fun onResponse(call: Call<JobPostItem?>, response: Response<JobPostItem?>) {
                Log.d("SUCCESS", "onResponse: ")
            }

            override fun onFailure(call: Call<JobPostItem?>, t: Throwable) {
                Log.d("FAIL", "onFailure: "+t)
            }
        })

        return inflater.inflate(R.layout.fragment_post_job_employer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PostJobEmployerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}