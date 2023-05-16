package com.example.assignment.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment.api.RetrofitBuild
import com.example.assignment.dataclass.Company
import com.example.assignment.dataclass.ResponseForUI
import com.example.assignment.dataclass.ValidationErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpEmployerViewModel : ViewModel() {
    val signUpResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    val signUpCompanyResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }


    val signUpEmployerResponse: MutableLiveData<ResponseForUI> by lazy {
        MutableLiveData<ResponseForUI>()
    }

    lateinit var companyDetails: Company
    var selectedCompany = ""
    var isNew = false

    fun submitCompany(input: Company) {

        val build = RetrofitBuild.build().validateCompany(
            input.name,
            input.contact_number,
            input.email,
            input.reg_no,
            input.location,
            input.description,
        )

        build.enqueue(object : Callback<Company?> {
            override fun onResponse(call: Call<Company?>, response: Response<Company?>) {
                if (response.isSuccessful) {
                    signUpCompanyResponse.value = ResponseForUI(true, "")
                    companyDetails = input
                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ValidationErrorResponse::class.java
                    )
                    signUpCompanyResponse.value = ResponseForUI(false, error.message)
                    Log.d("login", "onResponse: $error")
                    println("err1")
                } else { //unknown error
                    signUpCompanyResponse.value = ResponseForUI(false, "Something Went Wrong")
                }
            }

            override fun onFailure(call: Call<Company?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)

                signUpCompanyResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")
            }
        })
    }

    lateinit var registerBuild: Call<SignUpItem>
    fun signUpEmployer(input: SignUpItem, passwordConfirmation: String) {
        if (isNew) {
            registerBuild = RetrofitBuild.build().register(
                input.email,
                input.password,
                passwordConfirmation,
                input.phone,
                input.address,
                input.description,
                input.name,
                true,
                companyDetails.name,
                companyDetails.contact_number,
                companyDetails.email,
                companyDetails.reg_no,
                companyDetails.location,
                companyDetails.description,
                true,
            )
        } else {
//            println("fuck")
            registerBuild = RetrofitBuild.build().register(
                input.email,
                input.password,
                passwordConfirmation,
                input.phone,
                input.address,
                input.description,
                input.name,
                true,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                selectedCompany,
            )
        }

        registerBuild.enqueue(object : Callback<SignUpItem?> {
            override fun onResponse(call: Call<SignUpItem?>, response: Response<SignUpItem?>) {
                if (response.isSuccessful) {
                    signUpEmployerResponse.value = (ResponseForUI(true, ""))
                } else if (response.code() == 422) { //validation fails
                    val error = Gson().fromJson(
                        response.errorBody()!!.string(),
                        ValidationErrorResponse::class.java
                    )
                    println("err1")
                    signUpEmployerResponse.value = (ResponseForUI(false, error.message))
                  println("hehehe"+signUpEmployerResponse.value)

                } else { //unknown error
                    signUpEmployerResponse.value = (ResponseForUI(false, "Something Went Wrong"))
                }
            }

            override fun onFailure(call: Call<SignUpItem?>, t: Throwable) {
                Log.d("fail", "onFailure: " + t.message)
                signUpEmployerResponse.value =
                    (ResponseForUI(false, "Something Went Wrong. Kindly check your connection"))

            }
        })
    }


    val companyNameList: MutableLiveData<List<CompanyList>> by lazy {
        MutableLiveData<List<CompanyList>>()
    }


    fun getCompanyNameList() {
        val build = RetrofitBuild.build().getCompanyList()

        build.enqueue(object : Callback<List<CompanyList>?> {
            override fun onResponse(
                call: Call<List<CompanyList>?>,
                response: Response<List<CompanyList>?>
            ) {
                if (response.isSuccessful) {
                    signUpResponse.value = ResponseForUI(true, "")
                    companyNameList.value = response.body()!!
                    Log.d("CompanyNameList", "onResponse: " + response.body()!!)
                } else {
                    signUpResponse.value =
                        ResponseForUI(false, "Something Went Wrong.")
                    Log.d("Error", "onResponse: " + response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<List<CompanyList>?>, t: Throwable) {
                signUpResponse.value =
                    ResponseForUI(false, "Something Went Wrong. Kindly check your connection")
            }
        })
    }

    fun selectCompany(company: String) {
        this.selectedCompany = company
    }

    fun getCompanyArr(): ArrayList<String> {
        var companyArr = ArrayList<String>()

        for (x in companyNameList.value!!.toTypedArray())
            companyArr.add(x.name)

        return companyArr
    }

}