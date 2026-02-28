package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.address.AddressDTO

interface AddressRepository {
    suspend fun addAddress(addressDTO: AddressDTO): Result<Address>
    suspend fun getAddress(userId: Long): Result<List<Address>>
    suspend fun getAddressStore(): Result<Address>
}
