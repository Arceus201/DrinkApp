package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.dto.user.*
import com.example.drinkapp.data.resource.response.user.UserListResponse
import com.example.drinkapp.data.resource.response.user.UserResponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
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


}