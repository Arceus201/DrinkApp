package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.addressVN.City
import com.example.drinkapp.data.model.addressVN.District
import com.example.drinkapp.data.model.addressVN.Ward
import com.example.drinkapp.data.resource.Result

/**
 * Repository interface for Vietnamese address lookup operations.
 * Handles province (City), district, and ward lookup for Vietnamese administrative divisions.
 */
interface AddressVNRepository {
    /**
     * Get all provinces/cities in Vietnam.
     * @return Result containing list of City objects with nested districts and wards
     */
    suspend fun getProvinces(): Result<List<City>>
    
    /**
     * Get districts by province code.
     * @param provinceCode The code of the province
     * @return Result containing list of District objects for the specified province
     */
    suspend fun getDistrictsByProvince(provinceCode: Int): Result<List<District>>
    
    /**
     * Get wards by district code.
     * @param districtCode The code of the district
     * @return Result containing list of Ward objects for the specified district
     */
    suspend fun getWardsByDistrict(districtCode: Int): Result<List<Ward>>
}
