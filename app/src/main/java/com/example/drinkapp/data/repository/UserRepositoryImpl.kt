package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.user.UserLoginDTO
import com.example.drinkapp.data.resource.dto.user.UserManagerDTO
import com.example.drinkapp.data.resource.dto.user.UserSignUpDTO
import com.example.drinkapp.data.resource.dto.user.UserUpdateDTO
import com.example.drinkapp.data.resource.dto.user.UserUpdatePasswordDTO
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.UserApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : BaseRepository(), UserRepository {

    override suspend fun login(username: String, password: String): Result<User> =
        safeApiCall { apiService.loginCoroutine(UserLoginDTO(username, password)) }
            .map { it.user }

    override suspend fun signUp(user: User): Result<User> =
        safeApiCall { 
            apiService.registerCoroutine(
                UserSignUpDTO(
                    username = user.username,
                    phone = user.phone,
                    password = user.password
                )
            )
        }
        .map { it.user }

    override suspend fun updateUser(id: Long?, user: User): Result<User> =
        safeApiCall {
            apiService.updateUserCoroutine(
                UserUpdateDTO(
                    user_id = id ?: user.id,
                    username = user.username,
                    dob = user.dob
                )
            )
        }
        .map { it.user }

    override suspend fun updatePassword(id: Long?, oldPassword: String, newPassword: String): Result<User> =
        safeApiCall {
            apiService.updatePasswordCoroutine(
                UserUpdatePasswordDTO(
                    user_id = id ?: 0L,
                    current_password = oldPassword,
                    new_password = newPassword
                )
            )
        }
        .map { it.user }

    override suspend fun checkPassword(id: Long?, password: String): Result<User> =
        safeApiCall { apiService.checkPasswordCoroutine(id ?: 0L, password) }
            .map { it.user }

    override suspend fun getUserById(id: Long?): Result<User> =
        safeApiCall { apiService.getUserByIdCoroutine(id ?: 0L) }
            .map { it.user }

    override suspend fun getAllClient(): Result<List<User>> =
        safeApiCall { apiService.getAllClientCoroutine() }
            .map { it.users }

    override suspend fun updateStatusClient(id: Long?, status: Int): Result<List<User>> =
        safeApiCall {
            apiService.updateStatusClientCoroutine(
                UserManagerDTO(
                    id = id ?: 0L,
                    role = status.toLong()
                )
            )
        }
        .map { it.users }
}
