package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.assignment.api.Route
import com.example.assignment.api.JobPostItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JobPostFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_post_form)

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
        )

        build.createJobPost(testData).enqueue(object : Callback<JobPostItem?> {
            override fun onResponse(call: Call<JobPostItem?>, response: Response<JobPostItem?>) {
                Log.d("SUCCESS", "onResponse: ")
            }

            override fun onFailure(call: Call<JobPostItem?>, t: Throwable) {
                Log.d("FAIL", "onFailure: "+t)
            }
        })
    }
}