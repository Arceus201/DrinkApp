package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.address.AddressDTO

interface AddressRepository {
    suspend fun getAddressesByUserId(userId: Long?): Result<List<Address>>
    suspend fun addAddress(addressDTO: AddressDTO): Result<Address>
    suspend fun getAddressStore(): Result<Address>
    
    // Note: The following methods are defined in the spec but require backend API support
    // suspend fun updateAddress(id: Long?, address: Address): Result<Address>
    // suspend fun deleteAddress(id: Long?): Result<Boolean>
    // suspend fun setDefaultAddress(id: Long?, userId: Long?): Result<Address>
}
