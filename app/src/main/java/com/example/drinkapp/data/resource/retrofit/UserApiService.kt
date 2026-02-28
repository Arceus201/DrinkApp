package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.dto.user.*
import com.example.drinkapp.data.resource.response.user.UserListResponse
import com.example.drinkapp.data.resource.response.user.UserResponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {
    @POST(ApiConstant.API_KEY_USER_REGISTER)
    fun register(@Body userSignup: UserSignUpDTO): Call<UserResponse>

    @POST(ApiConstant.API_KEY_USER_LOGIN)
    fun login(@Body userLogin: UserLoginDTO): Call<UserResponse>

    @PUT(ApiConstant.API_KEY_USER_UPDATE)
    fun updateUser(@Body  userUpdateDTO: UserUpdateDTO) : Call<UserResponse>

    @PUT(ApiConstant.API_KEY_USER_UPDATE_PASSWORD)
    fun updatePassword(@Body  userUpdatePasswordDTO: UserUpdatePasswordDTO) : Call<UserResponse>

    @PUT(ApiConstant.API_KEY_USER_UPDATE_STATUS)
    fun updateStatusClient(@Body  userManagerDTO: UserManagerDTO) : Call<UserListResponse>

    @GET(ApiConstant.API_KEY_USER_CHECK_PASSWORD)
    fun checkPassword(@Query("user_id") user_id: Long,
                      @Query("password") password: String) : Call<UserResponse>

    @GET(ApiConstant.API_KEY_USER_GET_BY_ID)
    fun getUserById(@Path("id") id: Long) : Call<UserResponse>

    @GET(ApiConstant.API_KEY_USER_GET_ALL)
    fun getAllClient() : Call<UserListResponse>

    // Coroutine versions
    @POST(ApiConstant.API_KEY_USER_REGISTER)
    suspend fun registerCoroutine(@Body userSignup: UserSignUpDTO): Response<UserResponse>

    @POST(ApiConstant.API_KEY_USER_LOGIN)
    suspend fun loginCoroutine(@Body userLogin: UserLoginDTO): Response<UserResponse>

    @PUT(ApiConstant.API_KEY_USER_UPDATE)
    suspend fun updateUserCoroutine(@Body  userUpdateDTO: UserUpdateDTO) : Response<UserResponse>

    @PUT(ApiConstant.API_KEY_USER_UPDATE_PASSWORD)
    suspend fun updatePasswordCoroutine(@Body  userUpdatePasswordDTO: UserUpdatePasswordDTO) : Response<UserResponse>

    @PUT(ApiConstant.API_KEY_USER_UPDATE_STATUS)
    suspend fun updateStatusClientCoroutine(@Body  userManagerDTO: UserManagerDTO) : Response<UserListResponse>

    @GET(ApiConstant.API_KEY_USER_CHECK_PASSWORD)
    suspend fun checkPasswordCoroutine(@Query("user_id") user_id: Long,
                      @Query("password") password: String) : Response<UserResponse>

    @GET(ApiConstant.API_KEY_USER_GET_BY_ID)
    suspend fun getUserByIdCoroutine(@Path("id") id: Long) : Response<UserResponse>

    @GET(ApiConstant.API_KEY_USER_GET_ALL)
    suspend fun getAllClientCoroutine() : Response<UserListResponse>
}