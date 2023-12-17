package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.model.addressVN.City
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressVNpiService {
    @GET(ApiConstant.API_KEY_GET_ADDRESS_VN)
    fun getAddressVN() : Call<List<City>>
}