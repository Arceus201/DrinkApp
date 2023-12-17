package com.example.drinkapp.data.resource.retrofit



import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.dto.size.SizeDTO
import com.example.drinkapp.data.resource.response.size.SizeResponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.http.*

interface SizeApiService {

    @POST(ApiConstant.API_KEY_SIZE_ADD)
    fun addSize(@Body sizeDTO: SizeDTO) : Call<SizeResponse>

    @PUT(ApiConstant.API_KEY_SIZE_UPDATE)
    fun updateSize(@Path("id") id: Long?, @Body size: Size): Call<SizeResponse>

    @GET(ApiConstant.API_KEY_SIZE_GET_ALL)
    fun getall(): Call<SizeResponse>
}