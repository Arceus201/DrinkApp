package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.address.AddressDTO
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.AddressApiService
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val apiService: AddressApiService
) : BaseRepository(), AddressRepository {

    override suspend fun getAddressesByUserId(userId: Long?): Result<List<Address>> =
        safeApiCall { apiService.getAddressCoroutine(userId ?: 0) }
            .map { it.address }

    override suspend fun addAddress(addressDTO: AddressDTO): Result<Address> =
        safeApiCall { apiService.addAddressCoroutine(addressDTO) }
            .map { it.address }

    override suspend fun getAddressStore(): Result<Address> =
        safeApiCall { apiService.getAddressStoreCoroutine() }
            .map { it.address }
}
