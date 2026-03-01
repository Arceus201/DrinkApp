package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.model.addressVN.City
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Response
import retrofit2.http.GET

interface AddressVNpiService {
    @GET(ApiConstant.API_KEY_GET_ADDRESS_VN)
    suspend fun getAddressVN(): Response<List<City>>
}