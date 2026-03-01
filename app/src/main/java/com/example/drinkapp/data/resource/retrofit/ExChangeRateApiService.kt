package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.response.ExchangeRateReponse

import retrofit2.Response
import retrofit2.http.GET
import com.example.drinkapp.BuildConfig

interface ExChangeRateApiService {
        @GET(BuildConfig.KEY_EXCHANGERATEAPI +"/latest/USD")
        suspend fun getExchangeRates(): Response<ExchangeRateReponse>
}