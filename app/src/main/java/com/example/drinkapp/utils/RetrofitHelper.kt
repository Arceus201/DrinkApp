package com.example.drinkapp.utils

import retrofit2.Retrofit
import com.example.drinkapp.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val basrUrl = BuildConfig.API_KEY_URL_BE

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(basrUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}