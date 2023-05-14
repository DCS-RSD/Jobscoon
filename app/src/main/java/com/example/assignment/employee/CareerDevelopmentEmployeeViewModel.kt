package com.example.assignment.employee

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.*
import com.google.gson.Gson
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CareerDevelopmentEmployeeViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var currentUser: User

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

    val careerDevelopmentList: MutableLiveData<List<CareerDevelopmentItem>> by lazy {
        MutableLiveData<List<CareerDevelopmentItem>>()
    }

    private val _careerDevDetail = MutableLiveData<CareerDevelopmentItem>()
    val careerDevDetail: LiveData<CareerDevelopmentItem>
        get() = _careerDevDetail

    fun setCareerDevDetail(careerDevItem: CareerDevelopmentItem) {
        _careerDevDetail.value = careerDevItem
    }

    val careerDevId: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun getData() {
        val build = RetrofitBuild.build().getCareerDevelopment(
            sharedPreferences.getString("Token", "")!!
        )

        build.enqueue(object : Callback<List<CareerDevelopmentItem>?> {
            override fun onResponse(
                call: Call<List<CareerDevelopmentItem>?>,
                response: Response<List<CareerDevelopmentItem>?>
            ) {
                if (response.isSuccessful) {

                    getAllResponse.value = ResponseForUI(true, "")
                    careerDevelopmentList.value = response.body()!!
                    Log.d("success", "onResponse: " + careerDevelopmentList.value)

                } else { //unknown error

                    getAllResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<List<CareerDevelopmentItem>?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                getAllResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")


            }


        })

    }

    fun showCareerDev() {
        val build = RetrofitBuild.build().showCareer(
            sharedPreferences.getString("Token", "")!!, careerDevId.value!!
        )

        setCareerDevDetail(CareerDevelopmentItem()) //reset

        build.enqueue(object : Callback<CareerDevelopmentItem> {
            override fun onResponse(
                call: Call<CareerDevelopmentItem>,
                response: Response<CareerDevelopmentItem>
            ) {
                if (response.isSuccessful) {

                    showResponse.value = ResponseForUI(true, "")
                    setCareerDevDetail(response.body()!!)
                    Log.d("success", "onResponse: " + careerDevDetail.value)

                } else { //unknown error

                    showResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<CareerDevelopmentItem>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                showResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }

    fun postJoinData() {
        val build = RetrofitBuild.build().applyCareer(
            sharedPreferences.getString("Token", "")!!, careerDevId.value!!
        )

        build.enqueue(object : Callback<ValidationErrorResponse> {
            override fun onResponse(
                call: Call<ValidationErrorResponse>,
                response: Response<ValidationErrorResponse>
            ) {
                if (response.isSuccessful) {

                    applyResponse.value = ResponseForUI(true, "")
                    //jobApplicationItem.value = response.body()!!
                    //Log.d("success", "onResponse: " + jobApplicationItem.value)

                } else { //unknown error

                    applyResponse.value = ResponseForUI(false, "Something Went Wrong")

                }
            }

            override fun onFailure(call: Call<ValidationErrorResponse>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                applyResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")

            }
        })

    }

    fun postUnjoinData() {
        val build = RetrofitBuild.build().cancelCareer(
            sharedPreferences.getString("Token", "")!!, careerDevId.value!!
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