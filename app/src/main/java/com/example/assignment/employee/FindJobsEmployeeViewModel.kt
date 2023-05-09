package com.example.assignment.employee

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.JobPostItem
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindJobsEmployeeViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val getAllResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val showResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }
    val applyResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }


    val jobPostList: MutableLiveData<List<JobPostItem>> by lazy {
        MutableLiveData<List<JobPostItem>>()
    }

    private val _jobPostDetail = MutableLiveData<JobPostItem>()
    val jobPostDetail: LiveData<JobPostItem>
        get() = _jobPostDetail

    fun setJobPostDetail(jobPostItem: JobPostItem) {
        _jobPostDetail.value = jobPostItem
    }


    val jobPostId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }



    fun getData() {
        val build = RetrofitBuild.build().getJobPost(
            sharedPreferences.getString("Token", "")!!
        )

        build.enqueue(object : Callback<List<JobPostItem>?> {
            override fun onResponse(
                call: Call<List<JobPostItem>?>,
                response: Response<List<JobPostItem>?>
            ) {
                if (response.isSuccessful) {

                    getAllResponse.value = ResponseForUI(true, "")
                    jobPostList.value = response.body()!!
                    Log.d("success", "onResponse: " + jobPostList.value)

                } else { //unknown error

                    getAllResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<List<JobPostItem>?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                getAllResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")


            }


        })

    }

    fun showJobPost() {
        val build = RetrofitBuild.build().showJobPost(
            sharedPreferences.getString("Token", "")!!, jobPostId.value!!
        )

        setJobPostDetail(JobPostItem()) //reset

        build.enqueue(object : Callback<JobPostItem> {
            override fun onResponse(
                call: Call<JobPostItem>,
                response: Response<JobPostItem>
            ) {
                if (response.isSuccessful) {

                    showResponse.value = ResponseForUI(true, "")
                    setJobPostDetail(response.body()!!)
                    Log.d("success", "onResponse: " + jobPostDetail.value)

                } else { //unknown error

                    showResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<JobPostItem>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                showResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }

    fun postData() {
        val build = RetrofitBuild.build().postJobApplication(
            sharedPreferences.getString("Token", "")!!, jobPostId.value!!
        )

        build.enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {

                    applyResponse.value = ResponseForUI(true, "")
                    //jobApplicationItem.value = response.body()!!
                    //Log.d("success", "onResponse: " + jobApplicationItem.value)

                } else { //unknown error

                    applyResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                applyResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }
}