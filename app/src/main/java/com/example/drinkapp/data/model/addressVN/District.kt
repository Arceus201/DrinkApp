package com.example.drinkapp.data.model.addressVN

data class District(
    val name: String,
    val code: Int,
    val divisionType: String,
    val codename: String,
    val provinceCode: Int,
    val wards: List<Ward>
)

