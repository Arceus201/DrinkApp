package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.user.UserLoginDTO
import com.example.drinkapp.data.resource.dto.user.UserManagerDTO
import com.example.drinkapp.data.resource.dto.user.UserSignUpDTO
import com.example.drinkapp.data.resource.dto.user.UserUpdateDTO
import com.example.drinkapp.data.resource.dto.user.UserUpdatePasswordDTO
import com.example.drinkapp.data.resource.retrofit.UserApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val apiService: UserApiService
) : UserRepository {

    override suspend fun login(phone: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val userDto = UserLoginDTO(phone, password)
            val response = apiService.loginCoroutine(userDto)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.user)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun signUp(username: String, phone: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val userDto = UserSignUpDTO(username, phone, password)
            val response = apiService.registerCoroutine(userDto)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.user)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun updateUser(userUpdateDTO: UserUpdateDTO): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateUserCoroutine(userUpdateDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.user)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun updatePassword(userUpdatePasswordDTO: UserUpdatePasswordDTO): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updatePasswordCoroutine(userUpdatePasswordDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.user)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun updateStatusClient(userManagerDTO: UserManagerDTO): Result<List<User>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateStatusClientCoroutine(userManagerDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.users.isNotEmpty()) {
                    Result.Success(body.users)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun checkPassword(userId: Long, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.checkPasswordCoroutine(userId, password)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.user)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun getUserById(id: Long): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUserByIdCoroutine(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.user)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun getAllClient(): Result<List<User>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllClientCoroutine()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.users.isNotEmpty()) {
                    Result.Success(body.users)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }
}
