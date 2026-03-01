package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.addressVN.City
import com.example.drinkapp.data.model.addressVN.District
import com.example.drinkapp.data.model.addressVN.Ward
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.AddressVNpiService
import javax.inject.Inject

/**
 * Implementation of AddressVNRepository for Vietnamese address lookup operations.
 * Uses the Vietnamese address API (separate from main backend API).
 */
class AddressVNRepositoryImpl @Inject constructor(
    private val apiService: AddressVNpiService
) : BaseRepository(), AddressVNRepository {

    override suspend fun getProvinces(): Result<List<City>> {
        return safeApiCall { apiService.getAddressVN() }
    }

    override suspend fun getDistrictsByProvince(provinceCode: Int): Result<List<District>> {
        return safeApiCall { apiService.getAddressVN() }
            .map { cities: List<City> ->
                cities.find { city -> city.code == provinceCode }?.districts ?: emptyList()
            }
    }

    override suspend fun getWardsByDistrict(districtCode: Int): Result<List<Ward>> {
        return safeApiCall { apiService.getAddressVN() }
            .map { cities: List<City> ->
                cities.flatMap { city -> city.districts }
                    .find { district -> district.code == districtCode }?.wards ?: emptyList()
            }
    }
}
