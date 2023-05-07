package com.example.assignment.employee

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.*
import com.google.gson.Gson
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobDetailsEmployeeViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var currentUser: User

    val sharedPreferences = application.getSharedPreferences("User", Context.MODE_PRIVATE)
    val loginResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val jobPostList: MutableLiveData<JobPostItem> by lazy {
        MutableLiveData<JobPostItem>()
    }

    val jobApplicationList: MutableLiveData<List<JobApplicationItem>> by lazy {
        MutableLiveData<List<JobApplicationItem>>()
    }

    fun autoLogin() {
        val build = RetrofitBuild.build().autoLogin(
            sharedPreferences.getString("Token", "")!!,
            sharedPreferences.getString("Id", "")!!
        )



        build.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.isSuccessful) {
                    currentUser = response.body()!!
                } else {
                    //dialog prompt to ask user login again
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {

                Log.i("check", "onFailure: no")
            }
        })
    }

    fun getData(id: Int) {
        val build = RetrofitBuild.build().showJobPost(
            sharedPreferences.getString("Token", "")!!, id
        )

        build.enqueue(object : Callback<JobPostItem> {
            override fun onResponse(
                call: Call<JobPostItem>,
                response: Response<JobPostItem>
            ) {
                if (response.isSuccessful) {

                    loginResponse.value = ResponseForUI(true, "")
                    jobPostList.value = response.body()!!
                    Log.d("success", "onResponse: "+jobPostList.value)

                } else { //unknown error

                    loginResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<JobPostItem>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                loginResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }

    fun postData(id: Int) {
        val build = RetrofitBuild.build().(
            sharedPreferences.getString("Token", "")!!, id
        )

        build.enqueue(object : Callback<JobPostItem> {
            override fun onResponse(
                call: Call<JobPostItem>,
                response: Response<JobPostItem>
            ) {
                if (response.isSuccessful) {

                    loginResponse.value = ResponseForUI(true, "")
                    jobPostList.value = response.body()!!
                    Log.d("success", "onResponse: "+jobPostList.value)

                } else { //unknown error

                    loginResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<JobPostItem>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                loginResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }


    fun getJobAppliedData() {
        val build = RetrofitBuild.build().getJobApplication(
            sharedPreferences.getString("Token", "")!!
        )

        build.enqueue(object : Callback<List<JobApplicationItem>?> {
            override fun onResponse(
                call: Call<List<JobApplicationItem>?>,
                response: Response<List<JobApplicationItem>?>
            ) {
                if (response.isSuccessful) {

                    loginResponse.value = ResponseForUI(true, "")
                    jobApplicationList.value = response.body()!!
                    Log.d("success", "onResponse: "+jobApplicationList.value)

                } else { //unknown error

                    loginResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<List<JobApplicationItem>?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                loginResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }

        })

    }


}