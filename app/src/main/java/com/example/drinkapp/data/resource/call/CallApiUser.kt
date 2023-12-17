package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.dto.user.*
import com.example.drinkapp.data.resource.response.user.UserListResponse
import com.example.drinkapp.data.resource.response.user.UserResponse
import com.example.drinkapp.data.resource.retrofit.UserApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiUser {
    private val userApiService: UserApiService by lazy {
        RetrofitHelper.getInstance().create(UserApiService::class.java)
    }

    fun login(phone: String, password: String, listen: OnResultListener<User>) {
        val userDto = UserLoginDTO(phone, password)
        val call = userApiService.login(userDto)
        callApi(call, listen)
    }

    fun updateUser(userUpdateDTO: UserUpdateDTO, listen: OnResultListener<User>) {
        val call = userApiService.updateUser(userUpdateDTO)
        callApi(call, listen)
    }

    fun updatePassword(
        userUpdatePasswordDTO: UserUpdatePasswordDTO,
        listen: OnResultListener<User>
    ) {
        val call = userApiService.updatePassword(userUpdatePasswordDTO)
        callApi(call, listen)
    }

    fun updateStatusClient(
        userManagerDTO: UserManagerDTO,
        listen: OnResultListener<List<User>>
    ) {
        val call = userApiService.updateStatusClient(userManagerDTO)
        callApiList(call, listen)
    }

    fun checkPassword(user_id: Long, password: String, listen: OnResultListener<User>) {
        val call = userApiService.checkPassword(user_id, password)
        callApi(call, listen)
    }

    fun getUserById(id: Long, listen: OnResultListener<User>) {
        val call = userApiService.getUserById(id)
        callApi(call, listen)
    }

    fun getAllClient( listen: OnResultListener<List<User>>) {
        val call = userApiService.getAllClient()
        callApiList(call, listen)
    }

    fun signUp(
        username: String,
        phone: String,
        password: String,
        listen: OnResultListener<User>
    ) {
        val userDto = UserSignUpDTO(username, phone, password)
        val call = userApiService.register(userDto)
        callApi(call, listen)
    }

    private fun callApi(call: Call<UserResponse>, listen: OnResultListener<User>) {
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.user)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    private fun callApiList(call: Call<UserListResponse>, listen: OnResultListener<List<User>>) {
        call.enqueue(object : Callback<UserListResponse> {
            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.users)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    companion object {
        private var instance: CallApiUser? = null
        fun getInstance() = synchronized(this) {
            instance ?: CallApiUser().also { instance = it }
        }
    }
}