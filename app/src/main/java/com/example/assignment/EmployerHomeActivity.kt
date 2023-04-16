package com.example.assignment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.recycleviews.JobPostRecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployerHomeActivity : AppCompatActivity() {

    lateinit var jobAdapter: JobPostRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)

        val recycleView = findViewById<RecyclerView>(R.id.recycleView)
        recycleView.setHasFixedSize(true)
        recycleView.layoutManager = LinearLayoutManager(this)
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


        val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        val build = RetrofitBuild.build()
            .getJobPost(sharedPreferences.getString("Token","")!!)

        build.enqueue(object : Callback<List<JobPostItem>?> {
            override fun onResponse(
                call: Call<List<JobPostItem>?>,
                response: Response<List<JobPostItem>?>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()!!

                    jobAdapter = JobPostRecyclerAdapter(baseContext, responseBody)
                    jobAdapter.notifyDataSetChanged()
                    findViewById<RecyclerView>(R.id.recycleView).adapter = jobAdapter
                }else{
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<List<JobPostItem>?>, t: Throwable) {
                d("E", "onFailure: " + t.message)
            }
        })
    }

//    private fun initRecyclerView() {
//
//
//    }

}