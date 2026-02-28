package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.user.UserManagerDTO
import com.example.drinkapp.data.resource.dto.user.UserUpdateDTO
import com.example.drinkapp.data.resource.dto.user.UserUpdatePasswordDTO

interface UserRepository {
    suspend fun login(phone: String, password: String): Result<User>
    suspend fun signUp(username: String, phone: String, password: String): Result<User>
    suspend fun updateUser(userUpdateDTO: UserUpdateDTO): Result<User>
    suspend fun updatePassword(userUpdatePasswordDTO: UserUpdatePasswordDTO): Result<User>
    suspend fun updateStatusClient(userManagerDTO: UserManagerDTO): Result<List<User>>
    suspend fun checkPassword(userId: Long, password: String): Result<User>
    suspend fun getUserById(id: Long): Result<User>
    suspend fun getAllClient(): Result<List<User>>
}
