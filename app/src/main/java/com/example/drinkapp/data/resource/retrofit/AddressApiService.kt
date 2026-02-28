package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.dto.address.AddressDTO
import com.example.drinkapp.data.resource.response.address.AddressListReponse
import com.example.drinkapp.data.resource.response.address.AddressReponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApiService {
    @POST(ApiConstant.API_KEY_ADD_ADDRESS)
    fun addAddress(@Body addressDTO: AddressDTO): Call<AddressReponse>

    @GET(ApiConstant.API_KEY_GET_ADDRESS)
    fun getAddress(@Path("user_id")user_id: Long) :Call<AddressListReponse>

    @GET(ApiConstant. API_KEY_GET_STORE)
    fun getAddressStore() :Call<AddressReponse>

    // Coroutine versions
    @POST(ApiConstant.API_KEY_ADD_ADDRESS)
    suspend fun addAddressCoroutine(@Body addressDTO: AddressDTO): Response<AddressReponse>

    @GET(ApiConstant.API_KEY_GET_ADDRESS)
    suspend fun getAddressCoroutine(@Path("user_id")user_id: Long) :Response<AddressListReponse>

    @GET(ApiConstant. API_KEY_GET_STORE)
    suspend fun getAddressStoreCoroutine() :Response<AddressReponse>
}