package com.example.drinkapp.data.resource.retrofit



import com.example.drinkapp.data.model.Size
import com.example.drinkapp.data.resource.dto.size.SizeDTO
import com.example.drinkapp.data.resource.response.size.SizeResponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface SizeApiService {

    // Old callback methods (keep for backward compatibility)
    @POST(ApiConstant.API_KEY_SIZE_ADD)
    fun addSize(@Body sizeDTO: SizeDTO) : Call<SizeResponse>

    @PUT(ApiConstant.API_KEY_SIZE_UPDATE)
    fun updateSize(@Path("id") id: Long?, @Body size: Size): Call<SizeResponse>

    @GET(ApiConstant.API_KEY_SIZE_GET_ALL)
    fun getall(): Call<SizeResponse>

    // New coroutine methods
    @GET(ApiConstant.API_KEY_SIZE_GET_ALL)
    suspend fun getAllCoroutine(): Response<SizeResponse>

    @POST(ApiConstant.API_KEY_SIZE_ADD)
    suspend fun addSizeCoroutine(@Body sizeDTO: SizeDTO): Response<SizeResponse>

    @PUT(ApiConstant.API_KEY_SIZE_UPDATE)
    suspend fun updateSizeCoroutine(@Path("id") id: Long?, @Body size: Size): Response<SizeResponse>
}