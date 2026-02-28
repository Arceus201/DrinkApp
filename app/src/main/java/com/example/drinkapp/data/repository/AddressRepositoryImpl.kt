package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.address.AddressDTO
import com.example.drinkapp.data.resource.retrofit.AddressApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddressRepositoryImpl(
    private val apiService: AddressApiService
) : AddressRepository {

    override suspend fun addAddress(addressDTO: AddressDTO): Result<Address> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.addAddressCoroutine(addressDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.address)
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

    override suspend fun getAddress(userId: Long): Result<List<Address>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAddressCoroutine(userId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.address.isNotEmpty()) {
                    Result.Success(body.address)
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

    override suspend fun getAddressStore(): Result<Address> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAddressStoreCoroutine()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.address)
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
