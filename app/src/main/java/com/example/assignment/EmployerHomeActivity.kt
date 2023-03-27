package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.api.ApiInterface
import com.example.assignment.api.JobPost
import com.example.assignment.api.JobPostItem
import com.example.assignment.recycleviews.JobPostRecyclerAdapter
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmployerHomeActivity : AppCompatActivity() {

    lateinit var jobAdapter: JobPostRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)


        fetchData()
//        val viewApplicant = findViewById<Button>(R.id.view_button)
//        viewApplicant.setOnClickListener {
//            startActivity(Intent(this, ApplicantListActivity::class.java))
//        }
//
//        val editPostButton = findViewById<Button>(R.id.edit_button)
//        editPostButton.setOnClickListener {
//            startActivity(Intent(this, JobPostFormActivity::class.java))
//        }

    }

    private fun fetchData() {
        val build = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getString(R.string.api_link))
            .build()
            .create(ApiInterface::class.java)
            .getJobPost()

        build.enqueue(object : Callback<List<JobPostItem>?> {
            override fun onResponse(
                call: Call<List<JobPostItem>?>,
                response: Response<List<JobPostItem>?>
            ) {
                initRecyclerView()
                val responseBody = response.body()!!

                jobAdapter.submitList(responseBody)

            }

            override fun onFailure(call: Call<List<JobPostItem>?>, t: Throwable) {
                Log.d("E", "onFailure: " + t.message)
            }
        })
    }

    private fun initRecyclerView() {

        findViewById<RecyclerView>(R.id.recycleView).apply {
            layoutManager = LinearLayoutManager(this@EmployerHomeActivity)
            jobAdapter = JobPostRecyclerAdapter()
            adapter = jobAdapter
        }
    }

}