package com.example.drinkapp.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.drinkapp.BuildConfig

object RetrofitHepplerAddressVN {
    private const val basrUrl = BuildConfig.API_KEY_URL_ADDRESS_VN

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(basrUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}