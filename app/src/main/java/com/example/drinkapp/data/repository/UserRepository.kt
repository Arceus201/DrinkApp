package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.User
import com.example.drinkapp.data.resource.Result

interface UserRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun signUp(user: User): Result<User>
    suspend fun updateUser(id: Long?, user: User): Result<User>
    suspend fun updatePassword(id: Long?, oldPassword: String, newPassword: String): Result<User>
    suspend fun checkPassword(id: Long?, password: String): Result<User>
    suspend fun getUserById(id: Long?): Result<User>
    suspend fun getAllClient(): Result<List<User>>
    suspend fun updateStatusClient(id: Long?, status: Int): Result<List<User>>
}
