package com.example.assignment.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuild {
    companion object
    {
       fun build(): Route{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl("http://10.0.2.2:8000/api/")
            .build()
            .create(Route::class.java)
           }
    }
}