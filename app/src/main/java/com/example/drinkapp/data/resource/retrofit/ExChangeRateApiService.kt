package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.response.ExchangeRateReponse

import retrofit2.Call
import retrofit2.http.GET
import com.example.drinkapp.BuildConfig

interface ExChangeRateApiService {
        @GET(BuildConfig.KEY_EXCHANGERATEAPI +"/latest/USD")
        fun getExchangeRates(): Call<ExchangeRateReponse>
}