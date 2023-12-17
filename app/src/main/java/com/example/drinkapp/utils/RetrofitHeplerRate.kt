package com.example.drinkapp.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.drinkapp.BuildConfig

object RetrofitHeplerRate {
    private const val basrUrl = BuildConfig.API_KEY_URL_RATE

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(basrUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}